package my.test;

import org.glassfish.cafe.api.*;
import org.glassfish.cafe.api.bean.*;
import org.glassfish.cafe.api.bean.CommunicationEvent.Type;
import com.google.api.translate.Language;
import com.google.api.translate.Translate;

@CommunicationBean
public class ExampleBean {
    
    @Context
    CommunicationContext<IMConversation, UserParticipant, TextMessage> ctx;

    @CommunicationEvent(type=Type.MESSAGEARRIVED)
    public void handleMessageArrival() {

       Participant fromParty = ctx.getParticipant();
       Participant toParty = ctx.getCommunication().getInitiator();
       if (toParty.equals(fromParty)) {
           toParty = ctx.getCommunication().getParticipant();
       }

       TextMessage msg = ctx.getMessage();

       String fromString = ExampleServlet.getLanguage(fromParty.getName());
       String toString = ExampleServlet.getLanguage(toParty.getName());

       Translate.setHttpReferrer("localhost");
       
       Language from = Language.fromString(fromString);
       Language to = Language.fromString(toString);       

       String txt = msg.getText();
       try {
           //System.out.println("Translating " + txt + " from " + from + " to " + to);
           String tranlatedMessage = Translate.execute(txt, from, to);
           msg.setText(tranlatedMessage);
       } catch (Exception e) {
           e.printStackTrace();
       }
    }
    
}
