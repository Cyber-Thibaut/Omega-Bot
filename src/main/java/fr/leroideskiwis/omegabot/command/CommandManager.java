package fr.leroideskiwis.omegabot.command;

import fr.leroideskiwis.omegabot.events.EventManager;
import fr.leroideskiwis.omegabot.user.OmegaUser;
import fr.leroideskiwis.omegabot.user.UserManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;

import java.util.HashMap;
import java.util.Map;

/**
 * Manage the commands
 */
public class CommandManager {

    private final Map<String, Command> commands = new HashMap<>();
    private final UserManager userManager;
    private final JDA jda;

    public CommandManager(JDA jda, EventManager eventManager, UserManager userManager) {
        this.userManager = userManager;
        this.jda = jda;

        register(new TimeoutBombCommand(eventManager, userManager));
        register(new SoldeCommand(userManager));
        register(new GivePointsCommand(userManager));
        register(new TransferCommand(userManager));
        register(new LotteryCommand());
        register(new RussianRouletteCommand(userManager));

    }

    /**
     * Register a command
     * @param command the command
     */
    private void register(Command command) {
        String name = command.register().getName();
        commands.put(name, command);
    }

    /**
     * Register the commands in discord
     */
    public void registerInDiscord(){
        jda.updateCommands().addCommands(commands.values().stream().map(Command::register).toList()).queue();
    }

    /**
     * Execute the command
     * @param event the event
     */
    public void execute(SlashCommandInteraction event) {
        OmegaUser user = userManager.from(event.getMember());
        commands.entrySet().stream()
                .filter(command -> command.getKey().equalsIgnoreCase(event.getName()))
                .findFirst()
                .ifPresent(command -> command.getValue().execute(user, event));
    }
}