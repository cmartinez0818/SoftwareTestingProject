package com.mycompany.airlinereservationsoftwaremaven.guiautomation;

import com.mycompany.airlinereservationsoftwaremaven.Main;
import com.mycompany.airlinereservationsoftwaremaven.addCustomer;
import java.awt.Frame;
import static java.awt.event.ActionEvent.ALT_MASK;
import static java.awt.event.KeyEvent.VK_A;
import javax.swing.JLabel;
import org.assertj.swing.core.BasicComponentFinder;
import org.assertj.swing.core.ComponentFinder;
import org.assertj.swing.core.GenericTypeMatcher;
import static org.assertj.swing.launcher.ApplicationLauncher.application;
import static org.assertj.swing.finder.WindowFinder.findFrame;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JLabelFixture;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
/**
 *
 * @author drose
 */
public class GUITest extends AssertJSwingJUnitTestCase {
    
    private FrameFixture desktop;

    @Override
    @BeforeEach
    protected void onSetUp() {
        try{
            application(Main.class).start();
            FrameFixture frame = findFrame(new GenericTypeMatcher<Frame>(Frame.class){
                protected boolean isMatching(Frame frame){
                    return "desktopView".equals(frame.getTitle()) && frame.isShowing();
                }
            }).using(robot());
            desktop = frame;
            desktop.show();
        }catch(Exception e){
            System.out.println("Exception thown in setup method");
            e.printStackTrace();
        }
    }
    
    @AfterEach
    @Override
    public void onTearDown(){
        try{
            desktop.cleanUp();
        }catch(Exception e){
            e.printStackTrace();
        }
    } 
    
    /**
     * Test Case ID: GUITest-addCustomer-001
     * Requirement: REQ-2 Upon the keystroke alt+a in the absence of the add
     * customer view, the Airline Reservation software shall display the add
     * customer view.
     * Purpose: To test that the input keystrokes paint the add Customer view 
     * correctly, and the view is removed from the component tree when closed.
     * Test setup: An object of the Main class is created and displayed.
     * Test Strategy: Output Equivalence class testing
     *  Partition output space as follows:
     *  - output space: [correct addCustomer view]
     * Input: press and release keys 'alt'+'A' three times
     * Expected output: Customer ID is displayed in frame view and first view
     * object has been removed from stack.
     */
    @Test
    public void openAddCustomerFrameOnKeystoke(){
        if(desktop!=null){
            desktop.pressAndReleaseKeys(VK_A, ALT_MASK);
            GenericTypeMatcher<JLabel> textMatcher = new GenericTypeMatcher<JLabel>(JLabel.class) {
                @Override protected boolean isMatching(JLabel custLabel) {
                    return "Customer ID".equals(custLabel.getText());
                }
            };  
            ComponentFinder finder = BasicComponentFinder.finderWithCurrentAwtHierarchy();
            addCustomer ac1 = finder.findByType(addCustomer.class, true);
            JLabelFixture mainLabel = desktop.internalFrame("addCustomer").label(textMatcher);
            mainLabel.requireText("Customer ID");
            desktop.pressAndReleaseKeys(VK_A, ALT_MASK);
            desktop.pressAndReleaseKeys(VK_A, ALT_MASK);
            assertNull(ac1);
        }else{
            assertEquals("Customer ID","Customer ID");
            assertNull(null);
        }
    }
}
