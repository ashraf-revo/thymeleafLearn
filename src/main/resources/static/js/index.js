var stompClient = null;

var webRtcPeer;
var videoInput;
var videoOutput;

window.onload = function () {
    videoInput = document.getElementById('videoInput');
    videoOutput = document.getElementById('videoOutput');

    var socket = new SockJS('/revox/hello');
    stompClient = Stomp.over(socket);

    var csrfToken = $("meta[name='_csrf']").attr("content");
    var csrfHeaderName = $("meta[name='_csrf_header']").attr("content");
    var headers = {};
    headers[csrfHeaderName] = csrfToken;
    stompClient.connect(headers, function (frame) {
        stompClient.subscribe('/user/topic/message', function (answer) {
            var o = JSON.parse(answer.body);
            if (o.messageType == "SDPOFFER_MESSAGE") {
                webRtcPeer.processSdpAnswer(o.content);
            }
            else if (o.messageType == "ERROR") {
                alert(o.content);
            }
            else if (o.messageType == "INVITE_TO_PIPELINE_MESSAGE") {

            }
        });
    });
}

function CREATE_PIPELINE_MESSAGE(type) {
    if (type == 'One_To_Many') {
        webRtcPeer = kurentoUtils.WebRtcPeer.startSendOnly(videoInput, function (sdpOffer) {
                var message = JSON.stringify({
                    'messageType': "CREATE_PIPELINE_MESSAGE",
                    'content': sdpOffer,
                    'mediaPipelineType': type
                });
                stompClient.send("/app/message", {},
                    message
                )
                ;
            }
            , function (error) {
            }
        );


    } else if (type == 'One_To_One') {
        webRtcPeer = kurentoUtils.WebRtcPeer.startSendRecv(videoInput,videoOutput, function (sdpOffer) {
                var message = JSON.stringify({
                    'messageType': "CREATE_PIPELINE_MESSAGE",
                    'content': sdpOffer,
                    'mediaPipelineType': type
                });
                stompClient.send("/app/message", {},
                    message
                )
                ;
            }
            , function (error) {
            }
        );


    }

}

function INVITE_TO_PIPELINE_MESSAGE(to) {

    var message = JSON.stringify({
        'messageType': "INVITE_TO_PIPELINE_MESSAGE",
        'to': to
    });

    stompClient.send("/app/message", {}, message);
}


function JOIN_PIPELINE_MESSAGE(to, type) {
    if (type == 'One_To_Many') {
        webRtcPeer = kurentoUtils.WebRtcPeer.startRecvOnly(videoOutput, function (sdpOffer) {
            var message = JSON.stringify({
                'messageType': "JOIN_PIPELINE_MESSAGE",
                'content': sdpOffer,
                'to': to
            });
            stompClient.send("/app/message", {},
                message
            )
            ;

        }, function (error) {
        });
    }
    else if (type == 'One_To_One') {
        webRtcPeer = kurentoUtils.WebRtcPeer.startSendRecv(videoInput,videoOutput, function (sdpOffer) {
            var message = JSON.stringify({
                'messageType': "JOIN_PIPELINE_MESSAGE",
                'content': sdpOffer,
                'to': to
            });
            stompClient.send("/app/message", {},
                message
            )
            ;

        }, function (error) {
        });
    }
}

