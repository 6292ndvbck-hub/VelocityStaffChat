package staffchat

import com.velocitypowered.api.command.SimpleCommand
import com.velocitypowered.api.proxy.Player
import com.velocitypowered.api.proxy.ProxyServer
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor

class StaffChatCommand(private val server: ProxyServer) : SimpleCommand {
    override fun execute(invocation: SimpleCommand.Invocation) {
        val source = invocation.source()
        val args = invocation.arguments()

        // Le message si un gars essaie de parler pendant le mute
        if (StaffChatPlugin.isMuted) {
            source.sendMessage(Component.text("Euh... hello ? Réactive le chat, tu parles dans le vent là !", NamedTextColor.RED))
            return
        }

        if (args.isEmpty()) return

        val message = args.joinToString(" ")
        val senderName = if (source is Player) source.username else "Console"

        val broadcast = Component.text("[Staff] ", NamedTextColor.RED)
            .append(Component.text(senderName, NamedTextColor.YELLOW))
            .append(Component.text(": $message", NamedTextColor.WHITE))

        server.consoleCommandSource.sendMessage(broadcast)
        server.allPlayers.filter { it.hasPermission("staff.chat") }
            .forEach { it.sendMessage(broadcast) }
    }

    override fun hasPermission(invocation: SimpleCommand.Invocation): Boolean {
        return invocation.source().hasPermission("staff.chat")
    }
}