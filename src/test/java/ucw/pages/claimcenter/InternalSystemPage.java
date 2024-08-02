package ucw.pages.claimcenter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ucw.elements.Button;

import static java.lang.Thread.sleep;

public class InternalSystemPage extends ClaimCenter {
    private static final Logger logger = LogManager.getLogger(InternalSystemPage.class);
    private static final Button runFinancialEscalationBatchButton = Button.id("BatchProcessInfo:BatchProcessScreen:BatchProcessesLV:24:RunBatchWithoutNotify");
    private static final Button toolsMenuActionButton = Button.id("ServerTools:InternalToolsMenuActions-btnInnerEl");
    private static final Button returnToCCButton = Button.id("ServerTools:InternalToolsMenuActions:ReturnToApp-textEl");

    public void runFinancialEscalationBatch() {
        logger.info("Run Financial escalation batch in System internal page");
        click(runFinancialEscalationBatchButton);
        addStepInfoToReport("Clicked Financial escalation batch button");
        try {
            sleep(10000);
            click(toolsMenuActionButton);
            hoverAndClick(returnToCCButton);
        } catch (InterruptedException ignore) {}
    }
}
