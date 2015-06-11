var ConversationMessage = function ConversationMessage(MessageType, Content, Content1, From, To) {
    var MessageType;
    var Content;
    var Content1;
    var From;
    var To;

    this.constructor;
    this.MessageType = MessageType;
    this.Content = Content;
    this.Content1 = Content1;
    this.From = From;
    this.To = To;


}
var MessageType = function MessageType() {
    this.TEXT_MESSAGE = "TEXT_MESSAGE";
    this.LOGOUT_MESSAGE = "LOGOUT_MESSAGE";
    this.RELEASE_PIPELINE_MESSAGE = "RELEASE_PIPELINE_MESSAGE";
    this.SDPOFFER_MESSAGE = "SDPOFFER_MESSAGE";
    this.INVITE_TO_PIPELINE_MESSAGE = "INVITE_TO_PIPELINE_MESSAGE";
    this.ERROR = "ERROR";
    this.CREATE_PIPELINE_MESSAGE = "CREATE_PIPELINE_MESSAGE";
    this.JOIN_PIPELINE_MESSAGE = "JOIN_PIPELINE_MESSAGE";


}

console.log(JSON.stringify((new ConversationMessage(new MessageType().TEXT_MESSAGE, undefined))));