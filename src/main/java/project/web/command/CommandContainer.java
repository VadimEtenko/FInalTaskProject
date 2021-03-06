package project.web.command;

import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import project.web.command.bookedRoom.*;
import project.web.command.notification.*;
import project.web.command.requestByRoom.*;
import project.web.command.requesteWish.*;

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
		commands.put("noCommand", new NoCommand());

		// manager's commands
		commands.put("find-free-rooms", new FindFreeRoomsCommand());
		commands.put("list-request", new ListRequestedCommand());
		commands.put("list-booked", new ListBookedRoomCommand());
		commands.put("verify-application", new VerifyRequestedCommand());
		commands.put("update-booked",new UpdateBookedRoomCommand());
		commands.put("update-booked-list",new ListUpdateBookedRoomCommand());
		commands.put("request-wish-list",new ListUpdateBookedRoomCommand());
		commands.put("list-requests-wish", new ListRequestWishCommand());
		commands.put("plan-offer", new ListOfferToRequestWishCommand());
		commands.put("create-offer", new CreateOfferCommand());

		// client's commands
		commands.put("list-free-rooms", new ListFreeRoomsCommand());
		commands.put("create-request", new CreateRequestCommand());
		commands.put("list-user-requested", new ListUserRequestedCommand());
		commands.put("delete-reservation", new DeleteRequestedCommand());
		commands.put("notifications", new ListNotificationCommand());
		commands.put("delete-notification", new DeleteNotificationCommand());
		commands.put("plan-request", new PlanRequestCommand());
		commands.put("create-plan-request", new CreateRequestWishCommand());
		commands.put("list-offer-to-verify", new ListOffersToVerifyCommand());
		commands.put("apply-offer", new AgreeWithOffersCommand());

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