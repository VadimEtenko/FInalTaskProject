package project.web.command;

import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

/**
 * Holder for all commands.<br/>
 * 
 * @author V. Etenko
 * 
 */
public class CommandContainer {
	
	private static final Logger log = Logger.getLogger(CommandContainer.class);
	
	private static Map<String, Command> commands = new TreeMap<String, Command>();
	
	static {
		// common commands
		commands.put("login", new LoginCommand());
		commands.put("logout", new LogoutCommand());

		//commands.put("noCommand", new NoCommand());

		// manager's commands
		commands.put("listRequest", new ListRequestedCommand());
		commands.put("listBooked", new ListBookedRoomCommand());
		commands.put("verify-application", new VerifyRequestedCommand());
		commands.put("update-booked",new UpdateBookedRoomCommand());

		// client's commands
		commands.put("listFreeRooms", new ListFreeRoomsCommand());
		commands.put("makeRequest", new MakeRequestedCommand());
		commands.put("registration", new RegistrationCommand());

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