package dev.remine.smpcore.commands;

import dev.remine.smpcore.SMPCore;
import dev.remine.smpcore.karma.commands.KarmaAdminCommand;
import dev.remine.smpcore.lives.commands.LivesAdminCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class AdminCommand implements CommandExecutor {

    private SMPCore instance;

    public AdminCommand(SMPCore instance) {
        this.instance = instance;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length >= 1)
        {
            if (args[0].equalsIgnoreCase("setlives"))
                return new LivesAdminCommand().execute(instance, sender, command, args);
            if (args[0].equalsIgnoreCase("setkarma"))
                return new KarmaAdminCommand().execute(instance, sender, command, args);
        }



        return false;
    }
}
