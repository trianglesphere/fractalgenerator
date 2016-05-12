package fractalprojectgutow;

import fractalprojectgutow.fractals.TreeFractal;
import fractalprojectgutow.fractals.SierpinskiTriangle;
import fractalprojectgutow.fractals.OriginalFractal;
import fractalprojectgutow.fractals.extras.RandColor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import static java.lang.Math.pow;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Joshua Gutow
 */
public class FractalGUI implements ItemListener, ChangeListener, ActionListener {

    private static final int WIDTH = 600;
    private static final int HEIGHT = 800;
    private GridBagConstraints c;
    private JPanel fractal;
    private JPanel switcher;
    private JPanel fractalOpts;
    private JFrame frm;

    private final SierpinskiTriangle tri;
    private JPanel triOpts;
    private boolean triColorRand = true;

    private final TreeFractal tree;
    private JPanel treeOpts;
    private boolean treeColorRand = false;

    private final OriginalFractal orig;
    private JPanel origOpts;
    private boolean origColorRand = false;

    private final RandColor color;

    public FractalGUI() {
        color = new RandColor("rand");

        orig = new OriginalFractal(color);
        tri = new SierpinskiTriangle(color);
        tree = new TreeFractal(color);

        initGUI();
    }

    /**
     * Initializes the entire GUI.
     */
    private void initGUI() {
        frm = new JFrame("Joshua's Fractal Generator");

        // GridbagConstraints set up
        c = new GridBagConstraints();

        // Main frame set up
        frm.setLayout(new GridBagLayout());
        frm.setSize(WIDTH, HEIGHT);
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Adds the fractal switcher panel to the main frame.
        initSwitcherPanel();

        // Add the fractal to the frame.
        fractal = tri;
        addFractal(fractal);

        // Set up the fractal options
        initTriOpts();
        initTreeOpts();
        initOrigOpts();

        // Add the fractal specific options.
        fractalOpts = triOpts;
        addOptsPanel(triOpts);

        frm.setVisible(true);

    }

    /**
     * Initializes the Fractal Switcher Panel, and adds it to the frame.
     */
    private void initSwitcherPanel() {
        switcher = new JPanel(new GridBagLayout());
        c.gridy = 0;
        c.weightx = 0.0;
        c.weighty = 0.0;
        frm.add(switcher, c);

        // Button Group for the switcher options.
        ButtonGroup group = new ButtonGroup();

        // Settings for button location on the panel.
        c.gridy = 0;
        c.fill = GridBagConstraints.NONE;

        // Sierpinski Triangle Button
        JRadioButton triangleFractal = new JRadioButton("Sierpinski Triangle");
        triangleFractal.setName("triangle-fractal");
        triangleFractal.setSelected(true);
        triangleFractal.addItemListener(this);
        group.add(triangleFractal);
        c.gridx = 0;
        switcher.add(triangleFractal, c);

        // Tree Fractal Button
        JRadioButton treeFractal = new JRadioButton("Tree Fractal");
        treeFractal.setName("tree-fractal");
        treeFractal.addItemListener(this);
        group.add(treeFractal);
        c.gridx = 1;
        switcher.add(treeFractal, c);

        // Original Fractal Button
        JRadioButton origFractal = new JRadioButton("Apollonian Gasket");
        origFractal.setName("orig-fractal");
        origFractal.addItemListener(this);
        group.add(origFractal);
        c.gridx = 2;
        switcher.add(origFractal, c);
    }

    /**
     * Initializes the Triangle Fractal Options Panel.
     */
    private void initTriOpts() {
        triOpts = new JPanel(new GridBagLayout());

        // Triangle Size Slider
        JSlider triSize = new JSlider(JSlider.HORIZONTAL, 1, 10, 2);
        triSize.setName("size");
        triSize.addChangeListener(this);
        c.insets = new Insets(0, 0, 0, 0);
        c.gridx = 0;
        c.gridy = 1;
        triOpts.add(triSize, c);

        // Label for it.
        JLabel triSizeLabel = new JLabel("Triangle Size");
        c.gridy = 0;
        triOpts.add(triSizeLabel, c);

        // Color groupings slider.
        JSlider triColorSize = new JSlider(JSlider.HORIZONTAL, 0, 10, 0);
        triColorSize.setName("color");
        triColorSize.addChangeListener(this);
        c.gridx = 1;
        c.gridy = 1;
        triOpts.add(triColorSize, c);

        // Label for it.
        JLabel triColorSizeLabel = new JLabel("Color Grouping Size");
        c.gridy = 0;
        triOpts.add(triColorSizeLabel, c);

        // Color Mode Toggle
        JToggleButton TriToggle = new JToggleButton("Similar Colors");
        TriToggle.setName("tri-color-toggle");
        TriToggle.addItemListener(this);
        c.insets = new Insets(-35, 8, 0, 0);
        c.gridy = 1;
        c.gridx = 2;
        triOpts.add(TriToggle, c);

        // New Color Button.
        JButton TriNewColor = new JButton("New Colors");
        TriNewColor.addActionListener(this);
        c.gridx = 3;
        triOpts.add(TriNewColor, c);

        // Reset Button.
        JButton TriReset = new JButton("Reset");
        TriReset.setName("reset tri");
        TriReset.addActionListener(this);
        c.gridx = 4;
        triOpts.add(TriReset, c);

    }

    /**
     * Initializes the Tree Fractal Options Panel.
     */
    private void initTreeOpts() {
        treeOpts = new JPanel(new GridBagLayout());

        // Min length
        JSlider treeSize = new JSlider(JSlider.HORIZONTAL, 1, 20, 1);
        treeSize.setName("size");
        treeSize.addChangeListener(this);
        c.insets = new Insets(0, 0, 0, 0);
        c.gridx = 0;
        c.gridy = 1;
        treeOpts.add(treeSize, c);

        // Min Length Label
        JLabel treeSizeLabel = new JLabel("Min Length");
        c.gridy = 0;
        treeOpts.add(treeSizeLabel, c);

        // Branch Length Multiplier Slider
        JSlider treeMult = new JSlider(JSlider.HORIZONTAL, 1, 80, 50);
        treeMult.setName("frac");
        treeMult.addChangeListener(this);
        c.gridx = 1;
        c.gridy = 1;
        treeOpts.add(treeMult, c);

        // Branch Leng Frac Labell
        JLabel treeMultLabel = new JLabel("Branch Size Fraction");
        c.gridy = 0;
        treeOpts.add(treeMultLabel, c);

        // Angle Diff Slider
        JSlider treeAngle = new JSlider(JSlider.HORIZONTAL, 1, 90, 30);
        treeAngle.setName("angle");
        treeAngle.addChangeListener(this);
        c.gridx = 2;
        c.gridy = 1;
        treeOpts.add(treeAngle, c);

        // Angle Slider Labell
        JLabel treeAngleLabel = new JLabel("Angle Difference");
        c.gridy = 0;
        treeOpts.add(treeAngleLabel, c);

        // Color Mode Toggle
        JToggleButton TreeToggle = new JToggleButton("Random Colors");
        TreeToggle.setName("tree-color-toggle");
        TreeToggle.addItemListener(this);
        c.insets = new Insets(-35, 8, 0, 0);
        c.gridy = 1;
        c.gridx = 3;
        treeOpts.add(TreeToggle, c);

        // New Color Button
        JButton TreeNewColor = new JButton("New Colors");
        TreeNewColor.addActionListener(this);
        c.gridx = 4;
        treeOpts.add(TreeNewColor, c);

        // Reset Button
        JButton TreeReset = new JButton("Reset");
        TreeReset.setName("reset tree");
        TreeReset.addActionListener(this);
        c.gridx = 5;
        treeOpts.add(TreeReset, c);
    }

    /**
     * Initializes the Original Fractal Options Panel.
     */
    private void initOrigOpts() {
        origOpts = new JPanel(new GridBagLayout());

        // New Color Button
        JButton OrigNewColor = new JButton("New Colors");
        OrigNewColor.addActionListener(this);
        c.insets = new Insets(0, 8, 0, 0);
        c.gridx = 1;
        origOpts.add(OrigNewColor, c);

        // Color Mode Toggle
        JToggleButton OrigToggle = new JToggleButton("Random Colors");
        OrigToggle.setName("orig-color-toggle");
        OrigToggle.addItemListener(this);
        c.gridx = 0;
        origOpts.add(OrigToggle, c);

        // Reset Button
        JButton OrigReset = new JButton("Reset");
        OrigReset.setName("reset orig");
        OrigReset.addActionListener(this);
        c.gridx = 2;
        origOpts.add(OrigReset, c);
    }

    /**
     * Adds a fractal panel to the main frame.
     */
    public void addFractal(JPanel pnl) {
        // Remove the old fractal
        frm.remove(fractal);
        fractal = pnl;

        // Set up c so that is is represents the location of the fractal panel
        c.gridx = 0;
        c.gridy = 2;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.insets = new Insets(10, 10, 10, 10);

        // Add the new fractal, and paint the new fractal
        frm.add(fractal, c);
        frm.repaint();
        frm.revalidate();
    }

    /**
     * Adds an options panel to the main frame.
     */
    public void addOptsPanel(JPanel pnl) {
        // Remove the old fractal options
        frm.remove(fractalOpts);
        fractalOpts = pnl;

        // Set up c so that it represents the location of the fractal switcher panel
        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.NONE;
        c.weightx = 0.0;
        c.weighty = 0.0;
        c.insets = new Insets(0, 0, 0, 0);

        // Add the new fractal, and paint the new fractal
        frm.add(fractalOpts, c);
        frm.repaint();
        frm.revalidate();
    }

    /**
     * Slider Buttons
     */
    @Override
    public void stateChanged(ChangeEvent ce) {
        JSlider source = (JSlider) ce.getSource();
        if (!source.getValueIsAdjusting()) {

            if (fractal == tri) {
                if (source.getName().equals("size")) {
                    tri.setSize(5 * (int) pow(2, source.getValue()));
                }
                if (source.getName().equals("color")) {
                    tri.setColorSize(source.getValue());
                }
            }

            if (fractal == tree) {
                if (source.getName().equals("size")) {
                    tree.setBranchSize(source.getValue());
                }
                if (source.getName().equals("frac")) {
                    tree.setBranchLenMult(source.getValue());
                }
                if (source.getName().equals("angle")) {
                    tree.setAngleDiff(source.getValue());
                }
            }
        }

    }

    /**
     * Push Buttons - rest & color
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getActionCommand().equals("New Colors")) {
            color.regenColors();
            color.resetColors();
            fractal.repaint();
        } else if (ae.getSource() instanceof JButton) {
            JButton reset = (JButton) ae.getSource();
            if (reset.getName().equals("reset tri")) {
                JSlider size = (JSlider) triOpts.getComponent(0);
                size.setValue(2);

                JSlider colorSlider = (JSlider) triOpts.getComponent(2);
                colorSlider.setValue(0);

                JToggleButton tgl = (JToggleButton) triOpts.getComponent(4);
                tgl.setSelected(false);
                tgl.setText("Similar Colors");
                triColorRand = true;
                color.setRand(triColorRand);
                fractal.repaint();

            } else if (reset.getName().equals("reset tree")) {
                JSlider len = (JSlider) treeOpts.getComponent(0);
                len.setValue(1);

                JSlider frac = (JSlider) treeOpts.getComponent(2);
                frac.setValue(50);

                JSlider angle = (JSlider) treeOpts.getComponent(4);
                angle.setValue(30);

                JToggleButton tgl = (JToggleButton) treeOpts.getComponent(6);
                tgl.setSelected(false);
                tgl.setText("Random Colors");
                treeColorRand = false;
                color.setRand(treeColorRand);
                fractal.repaint();

            } else if (reset.getName().equals("reset orig")) {
                JToggleButton tgl = (JToggleButton) origOpts.getComponent(1);
                tgl.setSelected(false);
                tgl.setText("Random Colors");
                origColorRand = false;
                color.setRand(origColorRand);
                fractal.repaint();

            }
        }
    }

    /**
     * Toggle buttons - both radio & single toggle.
     */
    @Override
    public void itemStateChanged(ItemEvent ie) {
        JToggleButton btn = (JToggleButton) ie.getItem();

        switch (btn.getName()) {
            case "triangle-fractal":
                if (ie.getStateChange() == ItemEvent.SELECTED) {
                    addFractal(tri);
                    addOptsPanel(triOpts);
                    color.setRand(triColorRand);
                }
                break;
            case "tree-fractal":
                if (ie.getStateChange() == ItemEvent.SELECTED) {
                    addFractal(tree);
                    addOptsPanel(treeOpts);
                    color.setRand(treeColorRand);
                }
                break;
            case "orig-fractal":
                if (ie.getStateChange() == ItemEvent.SELECTED) {
                    addFractal(orig);
                    addOptsPanel(origOpts);
                    color.setRand(origColorRand);
                }
                break;
            case "tri-color-toggle":
                if (ie.getStateChange() == ItemEvent.SELECTED) {
                    btn.setText("Random Colors");
                    triColorRand = false;
                    color.setRand(triColorRand);
                    fractal.repaint();
                } else if (ie.getStateChange() == ItemEvent.DESELECTED) {
                    btn.setText("Similar Colors");
                    triColorRand = true;
                    color.setRand(triColorRand);
                    fractal.repaint();
                }
                break;
            case "tree-color-toggle":
                if (ie.getStateChange() == ItemEvent.SELECTED) {
                    btn.setText("Similar Colors");
                    treeColorRand = true;
                    color.setRand(treeColorRand);
                    fractal.repaint();
                } else if (ie.getStateChange() == ItemEvent.DESELECTED) {
                    btn.setText("Random Colors");
                    treeColorRand = false;
                    color.setRand(treeColorRand);
                    fractal.repaint();
                }
                break;
            case "orig-color-toggle":
                if (ie.getStateChange() == ItemEvent.SELECTED) {
                    btn.setText("Similar Colors");
                    origColorRand = true;
                    color.setRand(origColorRand);
                    fractal.repaint();
                } else if (ie.getStateChange() == ItemEvent.DESELECTED) {
                    btn.setText("Random Colors");
                    origColorRand = false;
                    color.setRand(origColorRand);
                    fractal.repaint();
                }
                break;
            default:
                break;
        }
    }
}
