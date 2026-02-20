package staffchat

import com.google.inject.Inject
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.plugin.Plugin
import com.velocitypowered.api.proxy.ProxyServer
import org.slf4j.Logger

@Plugin(id = "staffchat", name = "StaffChat", version = "1.0", authors = ["SlokiZAZA"])
class StaffChatPlugin @Inject constructor(val server: ProxyServer, val logger: Logger) {

    companion object {
        var isMuted: Boolean = false
    }

    @Subscribe
    fun onProxyInitialization(event: ProxyInitializeEvent) {
        val cm = server.commandManager

        cm.register(cm.metaBuilder("sc").build(), StaffChatCommand(server))
        cm.register(cm.metaBuilder("scmute").build(), ScMuteCommand(server))
        cm.register(cm.metaBuilder("permsc").build(), PermscCommand(server))
        cm.register(cm.metaBuilder("scsetup").build(), ScSetupCommand(server))

        logger.info("StaffChat activé ! Utilise scsetup en console pour configurer")
    }
}