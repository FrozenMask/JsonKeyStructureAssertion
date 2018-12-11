import org.apache.jmeter.assertions.gui.AbstractAssertionGui;
import org.apache.jmeter.gui.util.VerticalPanel;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jorphan.gui.JLabeledTextArea;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class JsonKeyStructureAssertionGui extends AbstractAssertionGui implements ChangeListener{

    /**
     *
     */
    private static final long serialVersionUID = -7661038908844183561L;
    private JLabeledTextArea jsonString = null;
    private JLabeledTextArea jsonKeyStructure = null;
    private JTextPane comments = null;

    public JsonKeyStructureAssertionGui() {
        init();
    }

    private void init() {
        setLayout(new BorderLayout());
        setBorder(makeBorder());
        add(makeTitlePanel(), BorderLayout.NORTH);

        VerticalPanel panel = new VerticalPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        jsonString = new JLabeledTextArea("JsonString Expected");
        jsonKeyStructure = new JLabeledTextArea("JsonKeyStructure To Be Asserted. (Don't modify it before save.)");

//		jsonKeyStructure.setEnabled(false);

        jsonString.addChangeListener(this);

        panel.add(jsonString);
        panel.add(jsonKeyStructure);

        panel.add(comments);

        add(panel, BorderLayout.CENTER);
    }

    @Override
    public void clearGui() {
        super.clearGui();
        jsonString.setText("");
        jsonKeyStructure.setText("");
    }

    @Override
    public String getLabelResource() {
        return super.getClass().getSimpleName();
    }

    public String getStaticLabel() {
        return "JSON Key Structure Assertion";
    }

    @Override
    public TestElement createTestElement() {
        JsonKeyStructureAssertion jtAssertion = new JsonKeyStructureAssertion();
        modifyTestElement(jtAssertion);
        return jtAssertion;
    }

    @Override
    public void modifyTestElement(TestElement element) {
        super.configureTestElement(element);

        if (element instanceof JsonKeyStructureAssertion) {
            JsonKeyStructureAssertion jtAssertion = (JsonKeyStructureAssertion) element;
            jtAssertion.setJsonString(jsonString.getText());
            jtAssertion.setJsonKeyStructure(jsonKeyStructure.getText());
        }
    }

    @Override
    public void configure(TestElement element) {
        super.configure(element);
        JsonKeyStructureAssertion jtAssertion = (JsonKeyStructureAssertion) element;
        jsonString.setText(jtAssertion.getJsonString());
        jsonKeyStructure.setText(jtAssertion.getJsonKeyStructure());
    }

    @Override
    public void stateChanged(ChangeEvent arg0) {
        JsonKeyStructureAssertion jtAssertion = new JsonKeyStructureAssertion();
        String jsonTlString = jtAssertion.generateJsonKeyStructure(jsonString.getText());

        jsonKeyStructure.setText(jsonTlString);

    }

}
