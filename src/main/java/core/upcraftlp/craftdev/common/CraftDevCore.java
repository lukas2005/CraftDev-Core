package core.upcraftlp.craftdev.common;

import core.upcraftlp.craftdev.proxy.CommonProxy;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(
        name = CraftDevReference.MODNAME,
        version = CraftDevReference.VERSION,
        acceptedMinecraftVersions = CraftDevReference.MCVERSIONS,
        modid = CraftDevReference.MODID,
        acceptableRemoteVersions = "*",
        updateJSON = CraftDevReference.UPDATE_JSON,
        guiFactory = CraftDevReference.GUI_FACTORY,
        certificateFingerprint = CraftDevReference.FORGE_FINGERPRINT
)
public class CraftDevCore {

    private static final Logger log =LogManager.getLogger(CraftDevReference.MODID);
    
    @Mod.Metadata(CraftDevReference.MODID)
    public static ModMetadata metaData;

    public static Logger getLogger() {
        return log;
    }
    
    @SidedProxy(clientSide = CraftDevReference.CLIENT_PROXY, serverSide = CraftDevReference.SERVER_PROXY)
    public static CommonProxy proxy;

    @Mod.Instance
    private static CraftDevCore instance;

    public static CraftDevCore getInstance() {
        return instance;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
        log.info("Pre-Initialization finished.");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
        log.info("Initialization finished.");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
        log.info("Post-Initialization finished.");
    }

    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        proxy.serverStarting(event);
        log.info("World initialization complete.");
    }

    @Mod.EventHandler
    public void onFingerprintViolation(FMLFingerprintViolationEvent event) {
        FMLLog.bigWarning(event.getSource() + " has a mismatching fingerprint key!");
        if(event.isDirectory()) {
            log.warn(event.getSource() + " is a directory!");
        }
        else {
            log.warn("Expected: " + event.getExpectedFingerprint());
            String res = "Got " + event.getFingerprints().size() + " known keys: ";
            for (String fingerPrint : event.getFingerprints()) res += "\n   - " + fingerPrint;
            log.warn(res);
        }
    }
    
}
