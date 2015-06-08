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
        stompClient.subscribe('/user/topic/Send', function (sdpAnswer) {
            webRtcPeer.processSdpAnswer(sdpAnswer.body);
        });
        stompClient.subscribe('/user/topic/Recive', function (sdpAnswer) {
            webRtcPeer.processSdpAnswer(sdpAnswer.body);
        });
    });
}

function Send() {
    webRtcPeer = kurentoUtils.WebRtcPeer.startSendOnly(videoInput, IwillSend, onError);
}

function Recive() {
    webRtcPeer = kurentoUtils.WebRtcPeer.startRecvOnly(videoOutput, IwillRecive, onError);
}

function IwillSend(sdpOffer) {
    stompClient.send("/app/IwillSend", {}, sdpOffer);
}
function IwillRecive(sdpOffer) {
    stompClient.send("/app/IwillRecive", {}, sdpOffer);
}

function onError(error) {
}


