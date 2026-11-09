// ui/CalculatorWindow.java
package ru.gr05506.ui;

import ru.gr05506.application.CalculationService;
import ru.gr05506.application.CommandHistory;
import ru.gr05506.domain.Calculation;
import ru.gr05506.domain.ListOfMethods;
import ru.gr05506.domain.RealisationMethod;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CalculatorWindow extends JFrame implements CalculatorView {

    private final JTextField formulaField = new JTextField(20);
    private final JTextField lowerField = new JTextField(10);
    private final JTextField upperField = new JTextField(10);
    private final JTextField intervalsField = new JTextField("100", 10);
    private final JComboBox<RealisationMethod> methodBox = new JComboBox<>(ListOfMethods.standard().toArray(new RealisationMethod[0]));
    private final JButton calculateButton = new JButton("Вычислить");
    private final JButton deleteButton = new JButton("Удалить");
    private final JButton undoButton = new JButton("Отменить");
    private final DefaultListModel<Calculation> listModel = new DefaultListModel<>();
    private final JList<Calculation> calculationList = new JList<>(listModel);

    private final CalculationFormPresenter formPresenter;
    private final CalculationListPresenter listPresenter;

    public CalculatorWindow(CalculationService service,
                            CommandHistory history
                            ) {
        this.formPresenter = new CalculationFormPresenter(this, service, history);
        this.listPresenter = new CalculationListPresenter(this, service);

        setTitle("Определённый интеграл");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        buildLayout();
        setSize(760, 480);
        setLocationRelativeTo(null);
        listPresenter.start();
    }

    private void buildLayout() {
        JPanel form = new JPanel(new GridLayout(5, 2, 5, 5));
        form.add(new JLabel("Формула f(x):"));
        form.add(formulaField);
        form.add(new JLabel("Нижний предел a:"));
        form.add(lowerField);
        form.add(new JLabel("Верхний предел b:"));
        form.add(upperField);
        form.add(new JLabel("Число отрезков n:"));
        form.add(intervalsField);
        form.add(new JLabel("Метод:"));
        form.add(methodBox);

        JPanel buttons = new JPanel(new FlowLayout());
        buttons.add(calculateButton);
        buttons.add(deleteButton);
        buttons.add(undoButton);

        JPanel top = new JPanel(new BorderLayout(5, 5));
        top.add(form, BorderLayout.CENTER);
        top.add(buttons, BorderLayout.SOUTH);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(calculationList), BorderLayout.CENTER);

        calculationList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                                                          int index, boolean selected,
                                                          boolean hasFocus) {
                super.getListCellRendererComponent(list, value, index, selected, hasFocus);
                setText(describe((Calculation) value));
                return this;
            }
        });

        calculateButton.addActionListener(e -> {
            if (formPresenter.calculateClicked()) listPresenter.refresh();
        });
        deleteButton.addActionListener(e -> listPresenter.deleteSelected());
        undoButton.addActionListener(e -> formPresenter.undoLast());
    }

    // Строка списка: параметры решения и ответ
    private String describe(Calculation c) {
        return "f(x) = " + c.getFormula()
                + " | [" + String.format("%.4f", c.getLower())
                + "; " + String.format("%.4f", c.getUpper()) + "]"
                + " | " + c.getMethod()
                + " | n = " + c.getIntervals()
                + " | I = " + String.format("%.8f", c.getResult());
    }

    @Override public String getFormula()            { return formulaField.getText(); }
    @Override public String getLowerText()          { return lowerField.getText(); }
    @Override public String getUpperText()          { return upperField.getText(); }
    @Override public String getIntervalsText()      { return intervalsField.getText(); }
    @Override public RealisationMethod getMethod()  { return (RealisationMethod) methodBox.getSelectedItem(); }
    @Override public Calculation getSelected()      { return calculationList.getSelectedValue(); }

    @Override
    public void clearForm() {
        formulaField.setText("");
        lowerField.setText("");
        upperField.setText("");
    }

    @Override
    public void showCalculations(List<Calculation> calculations) {
        listModel.clear();
        for (Calculation c : calculations) listModel.addElement(c);
    }

    @Override
    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}