package staffchat

import com.velocitypowered.api.command.SimpleCommand
import com.velocitypowered.api.proxy.ProxyServer
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor

class ScSetupCommand(private val server: ProxyServer) : SimpleCommand {
    override fun execute(invocation: SimpleCommand.Invocation) {
        val args = invocation.arguments()
        val source = invocation.source()

        if (source is com.velocitypowered.api.proxy.Player) {
            source.sendMessage(Component.text("Console uniquement", NamedTextColor.RED))
            return
        }

        if (args.isEmpty()) {
            println("Usage: scsetup <pseudo>")
            return
        }

        val target = args[0]
        val cm = server.commandManager

        // On te donne les droits admin
        cm.executeAsync(server.consoleCommandSource, "lpv user $target permission set staff.chat true")
        cm.executeAsync(server.consoleCommandSource, "lpv user $target permission set staff.chat.admin true")

        // On configure les groupes staff
        val groups = listOf("guide", "modo", "supermodo", "responsable", "admin", "gerant")
        groups.forEach {
            cm.executeAsync(server.consoleCommandSource, "lpv group $it permission set staff.chat true")
        }

        println("Configuration finie pour $target et les groupes staff")
    }
}