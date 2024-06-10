package fr.leroideskiwis.omegabot.command;

import fr.leroideskiwis.omegabot.user.OmegaUser;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.requests.restaction.interactions.ReplyCallbackAction;

import java.awt.*;

public class AboutCommand implements Command{

    private long launchTime = System.currentTimeMillis();

    @Override
    public SlashCommandData commandData() {
        return Commands.slash("about", "Informations à propos du bot")
                .addOption(OptionType.BOOLEAN, "globalvisible", "Afficher à tout le monde ?");
    }

    @Override
    public void execute(OmegaUser user, SlashCommandInteraction event) {
        EmbedBuilder builder = new EmbedBuilder().setTitle("Informations à propos de ce bot").setColor(Color.ORANGE);

        User author = event.getGuild().getMemberById("327795708897787904").getUser();

        builder.addField("Github (n'hésitez pas à contribuer):", "https://github.com/LeRoiDesKiwis/Omega-Bot", false);
        builder.addField("Uptime", formatTime(System.currentTimeMillis() - launchTime), false);
        builder.setFooter("Développeur: "+author.getEffectiveName(), author.getAvatarUrl());
        builder.setThumbnail(event.getJDA().getSelfUser().getAvatarUrl());

        ReplyCallbackAction replyCallbackAction = event.replyEmbeds(builder.build());
        OptionMapping option = event.getOption("globalvisible");
        if(option == null || !option.getAsBoolean()) replyCallbackAction.setEphemeral(true);
        replyCallbackAction.queue();
    }

    private String formatTime(long l) {
        long seconds = l / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;

        return days + "j " + hours % 24 + "h " + minutes % 60 + "min " + seconds % 60 + "s";
    }

    @Override
    public int price() {
        return 0;
    }

    @Override
    public boolean isLoggable() {
        return false;
    }
}
