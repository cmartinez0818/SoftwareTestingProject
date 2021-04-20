package com.mycompany.airlinereservationsoftwaremaven.guiautomation;

import com.mycompany.airlinereservationsoftwaremaven.Main;
import com.mycompany.airlinereservationsoftwaremaven.addCustomer;
import java.awt.Frame;
import static java.awt.event.KeyEvent.VK_F1;
import javax.swing.JLabel;
import org.assertj.swing.core.BasicComponentFinder;
import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.ComponentFinder;
import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.core.Robot;
import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import static org.assertj.swing.launcher.ApplicationLauncher.application;
import static org.assertj.swing.finder.WindowFinder.findFrame;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JLabelFixture;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
/**
 *
 * @author drose
 */
public class GUITest {
    
    private FrameFixture desktop;
    private static Robot robo;
    
    @BeforeAll
    public static final void setUpOnce() {
      if (robo == null) {
        robo = BasicRobot.robotWithNewAwtHierarchy();
      }
      FailOnThreadViolationRepaintManager.install();
    }
    
    @BeforeEach
    public final void setUp() {
      this.onSetUp();
    }

    protected void onSetUp() {
        application(Main.class).start();
        FrameFixture frame = findFrame(new GenericTypeMatcher<Frame>(Frame.class){
            protected boolean isMatching(Frame frame){
                return "desktopView".equals(frame.getTitle()) && frame.isShowing();
            }
        }).using(robot());
        desktop = frame;
        desktop.show();
    }

    @AfterAll
    public static final void tearDownOnce() {
      robo.cleanUp();
      FailOnThreadViolationRepaintManager.uninstall();
    }

    @AfterEach
    public final void tearDown() {
        this.onTearDown();
    }

    protected void onTearDown() {
    }

    protected static final Robot robot() {
        return robo;
    }
    /**
     * Test Case ID: GUITest-addCustomer-001
     * Requirement: REQ-51 Upon the keystroke F1 in the absence of the add
     * customer view, the Airline Reservation software shall display the add
     * customer view.
     * Purpose: To test that the input keystrokes paint the add Customer view 
     * correctly
     * Test setup: An object of the Main class is created and displayed.
     * Test Strategy: Output Equivalence class testing
     *  Partition output space as follows:
     *  - output space: [correct addCustomer view]
     * Input: press and release key 'F1' three times
     * Expected output: Customer ID is displayed in desktop view
     */
    @Test
    public void openAddCustomerFrameOnKeystoke(){
        desktop.pressAndReleaseKeys(VK_F1);
        GenericTypeMatcher<JLabel> textMatcher = new GenericTypeMatcher<JLabel>(JLabel.class) {
            @Override protected boolean isMatching(JLabel custLabel) {
                return "Customer ID".equals(custLabel.getText());
            }
        };
        JLabelFixture mainLabel = desktop.internalFrame("addCustomer").label(textMatcher);
        mainLabel.requireText("Customer ID");
        desktop.pressAndReleaseKeys(VK_F1);
        desktop.pressAndReleaseKeys(VK_F1);
        mainLabel = desktop.internalFrame("addCustomer").label(textMatcher);
        mainLabel.requireText("Customer ID");
    }
}
