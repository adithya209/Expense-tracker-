    private String description;
    private double amount;

    public Expense(String description, double amount) {
        this.description = description;
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Description: " + description + ", Amount: $" + amount;
    }
}

public class ExpenseTrackerGUI {
    private List<Expense> expenses = new ArrayList<>();
    private double totalExpenses = 0.0;
    private double budgetLimit = 0.0;

    private JFrame frame;
    private JTextArea textArea;
    private JTextField descriptionField;
    private JTextField amountField;
    private JTextField budgetField;
    private JLabel budgetLabel;
    private JButton deleteExpenseButton;
    private boolean budgetEntered = false;

    public ExpenseTrackerGUI() {
        frame = new JFrame("Expense Tracker");
        frame.setSize(500, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createGUI();
        frame.setVisible(true);
    }

    import javax.swing.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

class Expense {
    private String description;
    private double amount;

    public Expense(String description, double amount) {
        this.description = description;
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Description: " + description + ", Amount: $" + amount;
    }
}

public class ExpenseTrackerGUI {
    private List<Expense> expenses = new ArrayList<>();
    private double totalExpenses = 0.0;
    private double budgetLimit = 0.0;

    private JFrame frame;
    private JTextArea textArea;
    private JTextField descriptionField;
    private JTextField amountField;
    private JTextField budgetField;
    private JLabel budgetLabel;
    private JButton deleteExpenseButton;
    private boolean budgetEntered = false;

    public ExpenseTrackerGUI() {
        frame = new JFrame("Expense Tracker");
        frame.setSize(500, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createGUI();
        frame.setVisible(true);
    }

    private void createGUI() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        frame.add(panel);

        JLabel titleLabel = new JLabel("Expense Tracker");
        titleLabel.setBounds(10, 10, 200, 25);
        panel.add(titleLabel);

        JLabel budgetPromptLabel = new JLabel("Enter Budget:");
        budgetPromptLabel.setBounds(10, 40, 100, 25);
        panel.add(budgetPromptLabel);

        budgetField = new JTextField();
        budgetField.setBounds(100, 40, 150, 25);
        panel.add(budgetField);

        JButton setBudgetButton = new JButton("Set Budget");
        setBudgetButton.setBounds(270, 40, 120, 25);
        panel.add(setBudgetButton);

        JLabel descriptionLabel = new JLabel("Description:");
        descriptionLabel.setBounds(10, 80, 100, 25);
        panel.add(descriptionLabel);

        descriptionField = new JTextField();
        descriptionField.setBounds(100, 80, 150, 25);
        panel.add(descriptionField);

        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setBounds(10, 110, 100, 25);
        panel.add(amountLabel);

        amountField = new JTextField();
        amountField.setBounds(100, 110, 150, 25);
        panel.add(amountField);

        JButton addExpenseButton = new JButton("Add Expense");
        addExpenseButton.setBounds(10, 150, 150, 25);
        panel.add(addExpenseButton);

        deleteExpenseButton = new JButton("Delete Expense");
        deleteExpenseButton.setBounds(10, 180, 150, 25);
        deleteExpenseButton.setEnabled(false);
        panel.add(deleteExpenseButton);

        JButton viewExpensesButton = new JButton("View Expenses");
        viewExpensesButton.setBounds(10, 210, 150, 25);
        panel.add(viewExpensesButton);

        budgetLabel = new JLabel("Budget Remaining: $0.00");
        budgetLabel.setBounds(180, 10, 300, 25);
        panel.add(budgetLabel);

        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(180, 80, 280, 180);
        panel.add(scrollPane);

        setBudgetButton.addActionListener(e -> setBudget());
        addExpenseButton.addActionListener(e -> addExpense());
        deleteExpenseButton.addActionListener(e -> deleteExpense());
        viewExpensesButton.addActionListener(e -> viewExpenses());
    }

    private void setBudget() {
        if (budgetEntered) {
            JOptionPane.showMessageDialog(frame, "Budget already set. Cannot change it.");
            return;
        }

        try {
            double enteredBudget = Double.parseDouble(budgetField.getText());
            if (enteredBudget <= 0) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid budget greater than zero.");
                return;
            }
            budgetLimit = enteredBudget;
            budgetEntered = true;
            updateBudgetLabel();
            JOptionPane.showMessageDialog(frame, "Budget set successfully: $" + formatBudget(budgetLimit));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Invalid input. Please enter a valid number.");
        }
    }

    private void addExpense() {
        if (!budgetEntered) {
            JOptionPane.showMessageDialog(frame, "Please set the budget before adding expenses.");
            return;
        }

        String description = descriptionField.getText();
        String amountStr = amountField.getText();

        if (description.isEmpty() || amountStr.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter both description and amount.");
            return;
        }

        try {
            double amount = Double.parseDouble(amountStr);

            while (totalExpenses + amount > budgetLimit && !expenses.isEmpty()) {
                Expense removed = expenses.remove(0);
                totalExpenses -= removed.getAmount();
            }

            if (totalExpenses + amount > budgetLimit) {
                JOptionPane.showMessageDialog(frame, "Warning: Adding this expense will exceed the budget limit.");
                deleteExpenseButton.setEnabled(true);
                budgetLimit = 0;
                updateBudgetLabel();
                return;
            }

            Expense newExpense = new Expense(description, amount);
            expenses.add(newExpense);
            totalExpenses += amount;
            updateBudgetLabel();
            JOptionPane.showMessageDialog(frame, "Expense added successfully.");

            descriptionField.setText("");
            amountField.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Invalid amount. Please enter a valid number.");
        }
    }

    private void deleteExpense() {
        if (expenses.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No expenses to delete.");
            return;
        }

        Expense removed = expenses.remove(0);
        totalExpenses -= removed.getAmount();
        updateBudgetLabel();

        if (totalExpenses <= budgetLimit) {
            deleteExpenseButton.setEnabled(false);
        }

        JOptionPane.showMessageDialog(frame, "Expense deleted: " + removed.getDescription());
    }

    private void viewExpenses() {
        if (expenses.isEmpty()) {
            textArea.setText("No expenses recorded.");
        } else {
            StringBuilder sb = new StringBuilder("Expenses List:\n");
            for (Expense exp : expenses) {
                sb.append(exp.toString()).append("\n");
            }
            sb.append("Total Spent: $").append(formatBudget(totalExpenses));
            textArea.setText(sb.toString());
        }
    }

    private void updateBudgetLabel() {
        double remaining = budgetLimit - totalExpenses;
        budgetLabel.setText("Budget Remaining: $" + formatBudget(remaining));
        budgetLabel.setForeground(remaining < 0 ? java.awt.Color.RED : java.awt.Color.BLACK);
    }

    private String formatBudget(double value) {
        return new DecimalFormat("#.##").format(value);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ExpenseTrackerGUI());
    }
}

          
