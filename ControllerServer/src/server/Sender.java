package server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

import com.willwinder.universalgcodesender.AbstractController;
import com.willwinder.universalgcodesender.MainWindowServer;
import com.willwinder.universalgcodesender.MainWindowServer;
import com.willwinder.universalgcodesender.Utils;
import com.willwinder.universalgcodesender.MainWindowServer.ControlState;
import com.willwinder.universalgcodesender.i18n.Localization;

public class Sender {
	
	
	 public void sendButtonActionPerformed(List<String> commands){
		 try {
	            // This will throw an exception and prevent that other stuff from
	            // happening (clearing the table before its ready for clearing.
	            controller.isReadyToStreamFile();
		    	
	            // Mark the position in the table where the commands will begin.
	            //commandTable.setOffset();
	
	            if (true) {
	                controller.preprocessAndAppendGcodeCommand("G90");
	            }
	
	            controller.appendGcodeCommands(commands, null);

	            controller.beginStreaming();
	        } catch (Exception e) {
	       
	                     e.printStackTrace();
	           
	        }
 	}
}
