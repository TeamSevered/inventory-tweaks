import java.util.List;
import java.util.logging.Logger;

import net.minecraft.client.Minecraft;

import org.lwjgl.util.Point;


/**
 * The inventory and chest settings menu.
 * 
 * @author Jimeo Wan
 * 
 */
public abstract class InvTweaksGuiSettingsAbstract extends ug /* GuiScreen */ {

	protected static final Logger log = Logger.getLogger("InvTweaks");
    
    protected final static String ON = ": ON";
    protected final static String OFF = ": OFF";
    protected final static String DISABLE_CI = ": Disable CI";
    
    protected Minecraft mc;
    protected InvTweaksObfuscation obf;
    protected ug parentScreen;
    protected InvTweaksConfig config;

    protected final static String SCREEN_TITLE = "Inventory and chests settings";

    protected final static String LABEL_DONE = "Done";
    protected final static int ID_DONE = 200;

    public InvTweaksGuiSettingsAbstract(Minecraft mc, ug parentScreen,
            InvTweaksConfig config) {
        this.mc = mc;
        this.obf = new InvTweaksObfuscation(mc);
        this.parentScreen = parentScreen;
        this.config = config;
    }

    public void c() { /* initGui */

        List<Object> controlList = obf.getControlList(this);
        Point p = new Point();
        moveToButtonCoords(1, p);
        controlList.add(new zr(ID_DONE, p.getX() + 55, obf.getHeight(this) / 6 + 168, LABEL_DONE));

        // Save control list
        obf.setControlList(this, controlList);

    }
    
    public void a(int i, int j, float f) { /* drawScreen */
        k(); // Gui.drawDefaultBackground
        a(obf.getFontRenderer(), SCREEN_TITLE, obf.getWidth(this) / 2, 20, 0xffffff); // Gui.drawCenteredString
        super.a(i, j, f); // drawScreen
    }

    protected void a(zr guibutton) { /* actionPerformed */
        if (obf.getId(guibutton) == ID_DONE) {
            obf.displayGuiScreen(parentScreen);
        }
    }

    protected void moveToButtonCoords(int buttonOrder, Point p) {
        p.setX(obf.getWidth(this) / 2 - 155 + ((buttonOrder+1) % 2) * 160);
        p.setY(obf.getHeight(this) / 6 + (buttonOrder / 2) * 24);
    }

    protected void toggleBooleanButton(zr guibutton, String property, String label) {
        Boolean enabled = !new Boolean(config.getProperty(property));
        config.setProperty(property, enabled.toString());
        obf.setDisplayString(guibutton, computeBooleanButtonLabel(property, label));
    }

    protected String computeBooleanButtonLabel(String property, String label) {
        String propertyValue = config.getProperty(property);
        if (propertyValue.equals(InvTweaksConfig.VALUE_CI_COMPATIBILITY)) {
            return label + DISABLE_CI;
        } else {
            Boolean enabled = new Boolean(propertyValue);
            return label + ((enabled) ? ON : OFF);
        }
    }

}
