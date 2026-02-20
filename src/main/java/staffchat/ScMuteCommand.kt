package staffchat

import com.velocitypowered.api.command.SimpleCommand
import com.velocitypowered.api.proxy.ProxyServer
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor

class ScMuteCommand(private val server: ProxyServer) : SimpleCommand {
    override fun execute(invocation: SimpleCommand.Invocation) {
        StaffChatPlugin.isMuted = !StaffChatPlugin.isMuted

        val status = if (StaffChatPlugin.isMuted) "MUTE" else "ACTIF"
        val color = if (StaffChatPlugin.isMuted) NamedTextColor.RED else NamedTextColor.GREEN

        server.allPlayers.filter { it.hasPermission("staff.chat") }
            .forEach { it.sendMessage(Component.text("[StaffChat] Le chat est maintenant $status", color)) }
    }

    override fun hasPermission(invocation: SimpleCommand.Invocation): Boolean {
        return invocation.source().hasPermission("staff.chat.admin")
    }
}