package staffchat

import com.velocitypowered.api.command.SimpleCommand
import com.velocitypowered.api.proxy.ProxyServer
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor

class PermscCommand(private val server: ProxyServer) : SimpleCommand {
    override fun execute(invocation: SimpleCommand.Invocation) {
        val args = invocation.arguments()
        if (args.isEmpty()) {
            invocation.source().sendMessage(Component.text("Utilisation: /permsc <joueur>", NamedTextColor.RED))
            return
        }

        val target = args[0]
        // Exécute la commande de permission directement sur le proxy
        server.commandManager.executeAsync(server.consoleCommandSource, "lp user $target permission set staff.chat true")

        invocation.source().sendMessage(Component.text("Accès StaffChat donné à $target", NamedTextColor.GREEN))
    }
}