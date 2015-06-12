var stompClient = null;

var webRtcPeer;
var videoInput;
var videoOutput;

window.onload = function () {
    videoInput = document.getElementById('videoInput');
    videoOutput = document.getElementById('videoOutput');

    var socket = new SockJS('/hello');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
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

function CREATE_PIPELINE_MESSAGE() {
    webRtcPeer = kurentoUtils.WebRtcPeer.startSendOnly(videoInput, function (sdpOffer) {

            var message = JSON.stringify({
                'messageType': "CREATE_PIPELINE_MESSAGE",
                'content': sdpOffer,
                'mediaPipelineType': "One_To_Many"
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

function INVITE_TO_PIPELINE_MESSAGE() {

    var message = JSON.stringify({
        'messageType': "INVITE_TO_PIPELINE_MESSAGE",
        'to': "ashraf.abdelrasool"
    });

    stompClient.send("/app/message", {}, message);
}


function JOIN_PIPELINE_MESSAGE() {
    webRtcPeer = kurentoUtils.WebRtcPeer.startRecvOnly(videoOutput, function (sdpOffer) {
        var message = JSON.stringify({
            'messageType': "JOIN_PIPELINE_MESSAGE",
            'content': sdpOffer,
            'to': "revo"
        });
        stompClient.send("/app/message", {},
            message
        )
        ;

    }, function (error) {
    });
}

