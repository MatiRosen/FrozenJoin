package com.github.frcsty.command

import com.github.frcsty.configuration.MessageLoader
import com.github.frcsty.util.*
import me.mattstudios.mf.annotations.Alias
import me.mattstudios.mf.annotations.Command
import me.mattstudios.mf.annotations.Completion
import me.mattstudios.mf.annotations.Permission
import me.mattstudios.mf.annotations.SubCommand
import me.mattstudios.mf.base.CommandBase
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

@Command("frozenjoin")
@Alias("join", "fjoin")
class FormatCommand(private val messageLoader: MessageLoader) : CommandBase() {

    companion object {
        private const val SET_COMMAND = "set"
        private const val REMOVE_COMMAND = "remove"
        private const val PERMISSION = "frozenjoin.command.format"
        private const val JOIN_COMMAND = "join"
        private const val QUIT_COMMAND = "quit"
    }

    @SubCommand(SET_COMMAND)
    @Permission(PERMISSION)
    fun formatSetCommand(sender: CommandSender, @Completion("#players") player: Player?, @Completion("#format-argument") argument: String, @Completion("#format-message") message: String) {
        if (player == null) {
            sender.sendMessage(messageLoader.getMessage("customMessageInvalidPlayerMessage"))
            return
        }

        when (argument.lowercase()) {
            JOIN_COMMAND,
            QUIT_COMMAND -> {
                player.setCustomMessage(type = argument.lowercase(), message = message)
            }
            else -> {
                sender.sendMessage(messageLoader.getMessage("customMessageInvalidArgumentMessage"))
                return
            }
        }

        sender.sendTranslatedMessage(player, messageLoader.getMessage("customMessageSetTargetMessage")
                .replacePlaceholder("%type%", argument.lowercase())
                .replacePlaceholder("%message%", message.color()))
    }

    @SubCommand(REMOVE_COMMAND)
    @Permission(PERMISSION)
    fun formatRemoveCommand(sender: CommandSender, @Completion("#players") player: Player?, @Completion("#format-argument") argument: String) {
        if (player == null) {
            sender.sendMessage(messageLoader.getMessage("customMessageInvalidPlayerMessage"))
            return
        }

        when (argument.lowercase()) {
            JOIN_COMMAND,
            QUIT_COMMAND -> {
                player.removeCustomMessage(type = argument.lowercase())
            }
            else -> {
                sender.sendMessage(messageLoader.getMessage("customMessageInvalidArgumentMessage"))
                return
            }
        }

        sender.sendTranslatedMessage(player, messageLoader.getMessage("customMessageRemoveTargetMessage")
                .replacePlaceholder("%type%", argument.lowercase()))
    }
}