package com.incometaxcalculator.gui;

import com.incometaxcalculator.data.management.TaxpayerManager;
import com.incometaxcalculator.exceptions.WrongFileEndingException;
import com.incometaxcalculator.exceptions.WrongFileFormatException;
import com.incometaxcalculator.exceptions.WrongReceiptDateException;
import com.incometaxcalculator.exceptions.WrongReceiptKindException;
import com.incometaxcalculator.exceptions.WrongTaxpayerStatusException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.IOException;

public class GraphicalInterface extends JFrame {

  private final TaxpayerManager taxpayerManager = new TaxpayerManager();
  private final String taxpayersTRN = "";

  public void run() {
    EventQueue.invokeLater(() -> {
      try {
        init();
        this.setVisible(true);
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
  }

  public GraphicalInterface() {
  }

  private void init() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 450, 500);
    JPanel contentPane = new JPanel();
    contentPane.setBackground(new Color(204, 204, 204));
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    setContentPane(contentPane);
    contentPane.setLayout(null);

    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
             UnsupportedLookAndFeelException e) {
      e.printStackTrace();
    }

    JTextPane textPane = new JTextPane();
    textPane.setEditable(false);
    textPane.setBackground(new Color(153, 204, 204));
    textPane.setBounds(0, 21, 433, 441);

    JPanel fileLoaderPanel = new JPanel(new BorderLayout());
    JPanel boxPanel = new JPanel(new BorderLayout());
    JPanel taxRegistrationNumberPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
    JLabel TRN = new JLabel("Give the tax registration number:");
    JTextField taxRegistrationNumberField = new JTextField(20);
    taxRegistrationNumberPanel.add(TRN);
    taxRegistrationNumberPanel.add(taxRegistrationNumberField);
    JPanel loadPanel = new JPanel(new GridLayout(1, 2));
    loadPanel.add(taxRegistrationNumberPanel);
    fileLoaderPanel.add(boxPanel, BorderLayout.NORTH);
    fileLoaderPanel.add(loadPanel, BorderLayout.CENTER);
    JCheckBox txtBox = new JCheckBox("Txt file");
    JCheckBox xmlBox = new JCheckBox("Xml file");

    txtBox.addActionListener(ignored -> xmlBox.setSelected(false));

    xmlBox.addActionListener(ignored -> txtBox.setSelected(false));
    txtBox.doClick();
    boxPanel.add(txtBox, BorderLayout.WEST);
    boxPanel.add(xmlBox, BorderLayout.EAST);

    DefaultListModel<String> taxRegisterNumberModel = new DefaultListModel<>();

    JList<String> taxRegisterNumberList = new JList<>(taxRegisterNumberModel);
    taxRegisterNumberList.setBackground(new Color(153, 204, 204));
    taxRegisterNumberList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    taxRegisterNumberList.setSelectedIndex(0);
    taxRegisterNumberList.setVisibleRowCount(3);

    JScrollPane taxRegisterNumberListScrollPane = new JScrollPane(taxRegisterNumberList);
    taxRegisterNumberListScrollPane.setSize(300, 300);
    taxRegisterNumberListScrollPane.setLocation(70, 100);
    contentPane.add(taxRegisterNumberListScrollPane);

    JButton btnLoadTaxpayer = new JButton("Load Taxpayer");
    btnLoadTaxpayer.addActionListener(ignored -> {
      int answer = JOptionPane.showConfirmDialog(null, fileLoaderPanel, "",
          JOptionPane.OK_CANCEL_OPTION);
      if (answer == 0) {
        String taxRegistrationNumber = taxRegistrationNumberField.getText();
        while (taxRegistrationNumber.length() != 9 && answer == 0) {
          JOptionPane.showMessageDialog(null,
              "The tax  registration number must have 9 digit.\n" + " Try again.");
          answer = JOptionPane.showConfirmDialog(null, fileLoaderPanel, "",
              JOptionPane.OK_CANCEL_OPTION);
          taxRegistrationNumber = taxRegistrationNumberField.getText();
        }
        if (answer == 0) {
          int trn = 0;
          String taxRegistrationNumberFile;
          try {
            trn = Integer.parseInt(taxRegistrationNumber);
            if (txtBox.isSelected()) {
              taxRegistrationNumberFile = taxRegistrationNumber + "_INFO.txt";
            } else {
              taxRegistrationNumberFile = taxRegistrationNumber + "_INFO.xml";
            }
            if (taxpayerManager.containsTaxpayer(trn)) {
              JOptionPane.showMessageDialog(null, "This taxpayer is already loaded.");
            } else {
              taxpayerManager.loadTaxpayer(taxRegistrationNumberFile);
              taxRegisterNumberModel.addElement(taxRegistrationNumber);
            }
            // textPane.setText(taxpayersTRN);
          } catch (NumberFormatException e1) {
            JOptionPane.showMessageDialog(null,
                "The tax registration number must have only digits.");
          } catch (IOException e1) {
            JOptionPane.showMessageDialog(null, "The file doesn't exists.");
          } catch (WrongFileFormatException e1) {
            JOptionPane.showMessageDialog(null, "Please check your file format and try again.");
          } catch (WrongFileEndingException e1) {
            JOptionPane.showMessageDialog(null, "Please check your file ending and try again.");
          } catch (WrongTaxpayerStatusException e1) {
            JOptionPane.showMessageDialog(null, "Please check taxpayer's status and try again.");
          } catch (WrongReceiptKindException e1) {
            JOptionPane.showMessageDialog(null, "Please check receipts kind and try again.");
          } catch (WrongReceiptDateException e1) {
            JOptionPane.showMessageDialog(null,
                "Please make sure your date is " + "DD/MM/YYYY and try again.");
          }
        }

      }
    });
    btnLoadTaxpayer.setBounds(0, 0, 146, 23);
    contentPane.add(btnLoadTaxpayer);

    JButton btnSelectTaxpayer = new JButton("Select Taxpayer");
    btnSelectTaxpayer.addActionListener(ignored -> {
      if (taxpayerManager.containsTaxpayer()) {
        String trn = JOptionPane.showInputDialog(null,
            "Give the tax registration number " + "that you want to be displayed : ");
        if (trn != null) {
          int taxRegistrationNumber;
          try {
            taxRegistrationNumber = Integer.parseInt(trn);
            if (taxpayerManager.containsTaxpayer(taxRegistrationNumber)) {
              TaxpayerData taxpayerData = new TaxpayerData(taxRegistrationNumber, taxpayerManager);
              taxpayerData.setVisible(true);
            } else {
              JOptionPane.showMessageDialog(null, "This tax registration number isn't loaded.");
            }
          } catch (NumberFormatException e1) {
            JOptionPane.showMessageDialog(null, "You must give a tax registation number.");
          } catch (Exception e1) {
            e1.printStackTrace();
          }
        }
      } else {
        JOptionPane.showMessageDialog(null,
            "There isn't any taxpayer loaded. Please load one first.");
      }
    });
    btnSelectTaxpayer.setBounds(147, 0, 139, 23);
    contentPane.add(btnSelectTaxpayer);

    JButton btnDeleteTaxpayer = new JButton("Delete Taxpayer");
    btnDeleteTaxpayer.addActionListener(ignored -> {
      if (taxpayerManager.containsTaxpayer()) {
        String trn = JOptionPane.showInputDialog(null,
            "Give the tax registration number that you want to delete: ");
        int taxRegistrationNumber;
        try {
          taxRegistrationNumber = Integer.parseInt(trn);
          if (taxpayerManager.containsTaxpayer(taxRegistrationNumber)) {
            taxpayerManager.removeTaxpayer(taxRegistrationNumber);
            taxRegisterNumberModel.removeElement(trn);
          }
        } catch (NumberFormatException ignoredErr) {
        }
      } else {
        JOptionPane.showMessageDialog(null,
            "There isn't any taxpayer loaded. Please load one first.");
      }
    });
    btnDeleteTaxpayer.setBounds(287, 0, 146, 23);
    contentPane.add(btnDeleteTaxpayer);

    JTextField txtTaxRegistrationNumber = new JTextField();
    txtTaxRegistrationNumber.setEditable(false);
    txtTaxRegistrationNumber.setBackground(new Color(153, 204, 204));
    txtTaxRegistrationNumber.setFont(new Font("Tahoma", Font.BOLD, 14));
    txtTaxRegistrationNumber.setText("Tax Registration Number:");
    txtTaxRegistrationNumber.setBounds(70, 80, 300, 20);
    contentPane.add(txtTaxRegistrationNumber);
    txtTaxRegistrationNumber.setColumns(10);
  }
}