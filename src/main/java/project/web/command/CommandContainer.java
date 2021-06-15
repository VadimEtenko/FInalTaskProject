package project.web.command;

import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import project.web.command.bookedRoom.ListBookedRoomCommand;
import project.web.command.bookedRoom.ListUpdateBookedRoomCommand;
import project.web.command.bookedRoom.UpdateBookedRoomCommand;
import project.web.command.notification.DeleteNotificationCommand;
import project.web.command.notification.ListNotificationCommand;
import project.web.command.requested.*;

/**
 * Holder for all commands.<br/>
 * 
 * @author V. Etenko
 * 
 */
public class CommandContainer {
	
	private static final Logger log = Logger.getLogger(CommandContainer.class);
	
	private static final Map<String, Command> commands = new TreeMap<String, Command>();
	
	static {
		// common commands
		commands.put("login", new LoginCommand());
		commands.put("logout", new LogoutCommand());
		commands.put("registration", new RegistrationCommand());
		commands.put("change-local", new LocalChangeCommand());
		//commands.put("noCommand", new NoCommand());

		// manager's commands
		commands.put("listRequest", new ListRequestedCommand());
		commands.put("listBooked", new ListBookedRoomCommand());
		commands.put("verify-application", new VerifyRequestedCommand());
		commands.put("update-booked",new UpdateBookedRoomCommand());
		commands.put("update-booked-list",new ListUpdateBookedRoomCommand());

		// client's commands
		commands.put("listFreeRooms", new ListFreeRoomsCommand());
		commands.put("makeRequest", new MakeRequestedCommand());
		commands.put("list-user-requested", new ListUserRequestedCommand());
		commands.put("delete-reservation", new DeleteRequestedCommand());
		commands.put("notifications", new ListNotificationCommand());
		commands.put("delete-notification", new DeleteNotificationCommand());

		log.debug("Command container was successfully initialized");
		log.info("Number of commands --> " + commands.size());
	}

	/**
	 * Returns command object with the given name
	 * @param commandName
	 * 				Name of the command.
	 *
	 * @return Command object.
	 */
	public static Command get(String commandName) {
		if (commandName == null || !commands.containsKey(commandName)) {
			log.trace("Command not found, name --> " + commandName);
			return commands.get("noCommand"); 
		}
		return commands.get(commandName);
	}

}