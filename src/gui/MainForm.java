package gui;

import evaluator.Search;
import indexer.Indexer;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import javax.swing.*;
import javax.swing.text.DefaultCaret;

import static java.lang.System.currentTimeMillis;

/**
 * @author Alexandros Perrakis csd3826
 */

public class MainForm extends javax.swing.JFrame
{
    public MainForm()
    {
        initComponents();

    }

    private void initComponents()
    {
        this.setResizable(false);
        jPanelSnippet = new javax.swing.JPanel();
        JLabelDocNum = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanelMain = new javax.swing.JPanel();
        jLayeredPane3 = new javax.swing.JLayeredPane();
        jPanelIndex = new javax.swing.JPanel();
        jButtonIndex = new javax.swing.JButton();
        jButtonBrowseDocs = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jButtonBrowseOutput = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldOutput = new javax.swing.JTextField();
        jScrollPaneInfo = new javax.swing.JScrollPane();
        jTextAreaIndexInfo = new javax.swing.JTextArea();
        jProgressBarIndex = new javax.swing.JProgressBar();
        jTextFieldDocs = new javax.swing.JTextField();
        jTopLabel1 = new javax.swing.JLabel();
        jTextFieldMemoryLimit = new javax.swing.JTextField();
        jTopLabel2 = new javax.swing.JLabel();
        jPanelSearch = new javax.swing.JPanel();
        jButtonSearch = new javax.swing.JButton();
        jTextFieldQuery = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jScrollPaneAnswers = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jComboBoxType = new javax.swing.JComboBox<>();
        jLabelQueryTime = new javax.swing.JLabel();
        jPanelLeftSide = new javax.swing.JPanel();
        jPanelLeftTop = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButtonSideIndex = new javax.swing.JButton();
        jButtonSideSearch = new javax.swing.JButton();

        JLabelDocNum.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        JLabelDocNum.setText("1");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText("C:\\Users\\alexp\\Desktop\\hy463 project\\1_Ekfonisi");
        jLabel6.setToolTipText(null);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("Score: 0.62");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setText("The Java Tutorials have been written for JDK 8. Examples and practices described in this page don't take advantage of improvements introduced in later releases and might use technology no longer available.");

        javax.swing.GroupLayout jPanelSnippetLayout = new javax.swing.GroupLayout(jPanelSnippet);
        jPanelSnippet.setLayout(jPanelSnippetLayout);
        jPanelSnippetLayout.setHorizontalGroup(
                jPanelSnippetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanelSnippetLayout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(JLabelDocNum)
                                .addGap(18, 18, 18)
                                .addGroup(jPanelSnippetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel6)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 558, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelSnippetLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel5)
                                .addContainerGap())
        );
        jPanelSnippetLayout.setVerticalGroup(
                jPanelSnippetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelSnippetLayout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanelSnippetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(JLabelDocNum)
                                        .addComponent(jLabel7))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel5)
                                .addGap(12, 12, 12))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("HY463 - Alexandros Perrakis");
        setBackground(new java.awt.Color(51, 51, 255));

        jPanelMain.setBackground(new java.awt.Color(46, 51, 73));

        jLayeredPane3.setBackground(new java.awt.Color(51, 255, 153));
        jLayeredPane3.setOpaque(true);
        jLayeredPane3.setLayout(new java.awt.CardLayout());

        jPanelIndex.setBackground(new java.awt.Color(46, 51, 73));

        jButtonIndex.setBackground(new java.awt.Color(34, 41, 71));
        jButtonIndex.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButtonIndex.setForeground(new java.awt.Color(255, 255, 255));
        jButtonIndex.setIcon(new javax.swing.ImageIcon("C:\\Users\\alexp\\Desktop\\index.png")); // NOI18N
        jButtonIndex.setText("Index");
        jButtonIndex.setToolTipText(null);
        jButtonIndex.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButtonIndex.setBorderPainted(false);
        jButtonIndex.setContentAreaFilled(false);
        jButtonIndex.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonIndex.setMaximumSize(new java.awt.Dimension(39, 22));
        jButtonIndex.setMinimumSize(new java.awt.Dimension(39, 22));
        jButtonIndex.setOpaque(true);
        jButtonIndex.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseEntered(java.awt.event.MouseEvent evt)
            {
                jButtonIndexMouseEntered(evt);
            }

            public void mouseExited(java.awt.event.MouseEvent evt)
            {
                jButtonIndexMouseExited(evt);
            }
        });
        jButtonIndex.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButtonIndexActionPerformed(evt);
            }
        });

        jButtonBrowseDocs.setBackground(new java.awt.Color(34, 41, 71));
        jButtonBrowseDocs.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButtonBrowseDocs.setForeground(new java.awt.Color(255, 255, 255));
        jButtonBrowseDocs.setText("Browse");
        jButtonBrowseDocs.setToolTipText(null);
        jButtonBrowseDocs.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButtonBrowseDocs.setBorderPainted(false);
        jButtonBrowseDocs.setContentAreaFilled(false);
        jButtonBrowseDocs.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonBrowseDocs.setMaximumSize(new java.awt.Dimension(39, 22));
        jButtonBrowseDocs.setMinimumSize(new java.awt.Dimension(39, 22));
        jButtonBrowseDocs.setOpaque(true);
        jButtonBrowseDocs.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseEntered(java.awt.event.MouseEvent evt)
            {
                jButtonBrowseDocsMouseEntered(evt);
            }

            public void mouseExited(java.awt.event.MouseEvent evt)
            {
                jButtonBrowseDocsMouseExited(evt);
            }
        });
        jButtonBrowseDocs.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButtonBrowseDocsActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Document folder");

        jButtonBrowseOutput.setBackground(new java.awt.Color(34, 41, 71));
        jButtonBrowseOutput.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButtonBrowseOutput.setForeground(new java.awt.Color(255, 255, 255));
        jButtonBrowseOutput.setText("Browse");
        jButtonBrowseOutput.setToolTipText(null);
        jButtonBrowseOutput.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButtonBrowseOutput.setBorderPainted(false);
        jButtonBrowseOutput.setContentAreaFilled(false);
        jButtonBrowseOutput.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonBrowseOutput.setMaximumSize(new java.awt.Dimension(39, 22));
        jButtonBrowseOutput.setMinimumSize(new java.awt.Dimension(39, 22));
        jButtonBrowseOutput.setOpaque(true);
        jButtonBrowseOutput.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseEntered(java.awt.event.MouseEvent evt)
            {
                jButtonBrowseOutputMouseEntered(evt);
            }

            public void mouseExited(java.awt.event.MouseEvent evt)
            {
                jButtonBrowseOutputMouseExited(evt);
            }
        });
        jButtonBrowseOutput.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButtonBrowseOutputActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Index (Output) folder");

        jTextFieldOutput.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextFieldOutput.setToolTipText("Enter path of output folder");

        jScrollPaneInfo.setBackground(new java.awt.Color(24, 30, 54));
        jScrollPaneInfo.setBorder(null);
        jScrollPaneInfo.setForeground(new java.awt.Color(24, 30, 54));

        jTextAreaIndexInfo.setEditable(false);
        jTextAreaIndexInfo.setBackground(new java.awt.Color(24, 30, 54));
        jTextAreaIndexInfo.setColumns(20);
        jTextAreaIndexInfo.setFont(new java.awt.Font("TimesRoman", 0, 14)); // NOI18N
        jTextAreaIndexInfo.setForeground(new java.awt.Color(255, 255, 255));
        jTextAreaIndexInfo.setLineWrap(true);
        jTextAreaIndexInfo.setRows(5);
        jTextAreaIndexInfo.setToolTipText(null);
        jTextAreaIndexInfo.setBorder(null);
        jTextAreaIndexInfo.setMargin(new java.awt.Insets(20, 20, 20, 20));

        // Auto scroll the text area
        DefaultCaret caret = (DefaultCaret)jTextAreaIndexInfo.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        jScrollPaneInfo.setViewportView(jTextAreaIndexInfo);

        jProgressBarIndex.setForeground(new java.awt.Color(51, 255, 51));

        jTextFieldDocs.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextFieldDocs.setToolTipText("Enter path of  document folder");
        jTextFieldDocs.setMinimumSize(new java.awt.Dimension(10, 21));

        jTopLabel1.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jTopLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jTopLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jTopLabel1.setText("Memory limit");

        jTextFieldMemoryLimit.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextFieldMemoryLimit.setText("1024");

        jTopLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTopLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jTopLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jTopLabel2.setText("MB");

        javax.swing.GroupLayout jPanelIndexLayout = new javax.swing.GroupLayout(jPanelIndex);
        jPanelIndex.setLayout(jPanelIndexLayout);
        jPanelIndexLayout.setHorizontalGroup(
                jPanelIndexLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanelIndexLayout.createSequentialGroup()
                                .addGap(40, 40, 40)
                                .addGroup(jPanelIndexLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jProgressBarIndex, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanelIndexLayout.createSequentialGroup()
                                                .addComponent(jTextFieldOutput, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(4, 4, 4)
                                                .addComponent(jButtonBrowseOutput, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jScrollPaneInfo)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanelIndexLayout.createSequentialGroup()
                                                .addComponent(jTextFieldDocs, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(4, 4, 4)
                                                .addComponent(jButtonBrowseDocs, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(jPanelIndexLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanelIndexLayout.createSequentialGroup()
                                                .addGap(27, 27, 27)
                                                .addComponent(jButtonIndex, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanelIndexLayout.createSequentialGroup()
                                                .addGap(41, 41, 41)
                                                .addComponent(jTextFieldMemoryLimit, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jTopLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanelIndexLayout.createSequentialGroup()
                                                .addGap(24, 24, 24)
                                                .addComponent(jTopLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(32, Short.MAX_VALUE))
        );
        jPanelIndexLayout.setVerticalGroup(
                jPanelIndexLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelIndexLayout.createSequentialGroup()
                                .addContainerGap(45, Short.MAX_VALUE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanelIndexLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jButtonBrowseDocs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jTextFieldDocs, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(27, 27, 27)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanelIndexLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jButtonBrowseOutput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jTextFieldOutput, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanelIndexLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanelIndexLayout.createSequentialGroup()
                                                .addGap(21, 21, 21)
                                                .addGroup(jPanelIndexLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(jScrollPaneInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(jPanelIndexLayout.createSequentialGroup()
                                                                .addComponent(jTopLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(7, 7, 7)
                                                                .addGroup(jPanelIndexLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(jTextFieldMemoryLimit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(jTopLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                                .addGap(22, 22, 22)
                                                .addComponent(jProgressBarIndex, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(48, 48, 48))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelIndexLayout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jButtonIndex, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(44, 44, 44))))
        );

        jLayeredPane3.add(jPanelIndex, "card3");

        jPanelSearch.setBackground(new java.awt.Color(46, 51, 73));

        jButtonSearch.setBackground(new java.awt.Color(34, 41, 71));
        jButtonSearch.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButtonSearch.setForeground(new java.awt.Color(255, 255, 255));
        jButtonSearch.setIcon(new javax.swing.ImageIcon("C:\\Users\\alexp\\Desktop\\search.png")); // NOI18N
        jButtonSearch.setText("Search");
        jButtonSearch.setToolTipText(null);
        jButtonSearch.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButtonSearch.setBorderPainted(false);
        jButtonSearch.setContentAreaFilled(false);
        jButtonSearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonSearch.setMaximumSize(new java.awt.Dimension(39, 22));
        jButtonSearch.setMinimumSize(new java.awt.Dimension(39, 22));
        jButtonSearch.setOpaque(true);
        jButtonSearch.setEnabled(false);
        jButtonSearch.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseEntered(java.awt.event.MouseEvent evt)
            {
                jButtonSearchMouseEntered(evt);
            }

            public void mouseExited(java.awt.event.MouseEvent evt)
            {
                jButtonSearchMouseExited(evt);
            }
        });
        jButtonSearch.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButtonSearchActionPerformed(evt);
            }
        });

        jTextFieldQuery.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextFieldQuery.setToolTipText("What are you looking for?");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setIcon(new javax.swing.ImageIcon("C:\\Users\\alexp\\Desktop\\med.png")); // NOI18N

        jScrollPaneAnswers.setBackground(new java.awt.Color(51, 255, 204));
        jScrollPaneAnswers.setBorder(null);
        jScrollPaneAnswers.setForeground(new java.awt.Color(102, 255, 102));
        jScrollPaneAnswers.setOpaque(false);
        jScrollPaneAnswers.setRequestFocusEnabled(false);

        jPanel1.setBackground(new java.awt.Color(24, 30, 54));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 600, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 314, Short.MAX_VALUE)
        );

        jScrollPaneAnswers.setViewportView(jPanel1);

        jComboBoxType.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jComboBoxType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"diagnosis", "test", "treatment"}));

        jLabelQueryTime.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabelQueryTime.setForeground(new java.awt.Color(255, 255, 255));
        jLabelQueryTime.setSize(1000, 50);

        javax.swing.GroupLayout jPanelSearchLayout = new javax.swing.GroupLayout(jPanelSearch);
        jPanelSearch.setLayout(jPanelSearchLayout);
        jPanelSearchLayout.setHorizontalGroup(
                jPanelSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanelSearchLayout.createSequentialGroup()
                                .addGroup(jPanelSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelSearchLayout.createSequentialGroup()
                                                .addGap(31, 31, 31)
                                                .addComponent(jComboBoxType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jTextFieldQuery, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jButtonSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanelSearchLayout.createSequentialGroup()
                                                .addGap(226, 226, 226)
                                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanelSearchLayout.createSequentialGroup()
                                                .addGap(33, 33, 33)
                                                .addGroup(jPanelSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabelQueryTime, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jScrollPaneAnswers, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addContainerGap(47, Short.MAX_VALUE))
        );
        jPanelSearchLayout.setVerticalGroup(
                jPanelSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanelSearchLayout.createSequentialGroup()
                                .addGap(36, 36, 36)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanelSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanelSearchLayout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanelSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jTextFieldQuery, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jComboBoxType, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(jPanelSearchLayout.createSequentialGroup()
                                                .addGap(4, 4, 4)
                                                .addComponent(jButtonSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                                .addComponent(jLabelQueryTime)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPaneAnswers, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(38, 38, 38)));

        jLayeredPane3.add(jPanelSearch, "card2");

        jPanelLeftSide.setBackground(new java.awt.Color(24, 30, 54));

        jPanelLeftTop.setBackground(new java.awt.Color(24, 30, 54));

        jLabel1.setIcon(new javax.swing.ImageIcon("C:\\Users\\alexp\\Desktop\\search-engine.png")); // NOI18N

        javax.swing.GroupLayout jPanelLeftTopLayout = new javax.swing.GroupLayout(jPanelLeftTop);
        jPanelLeftTop.setLayout(jPanelLeftTopLayout);
        jPanelLeftTopLayout.setHorizontalGroup(
                jPanelLeftTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelLeftTopLayout.createSequentialGroup()
                                .addContainerGap(55, Short.MAX_VALUE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(37, 37, 37))
        );
        jPanelLeftTopLayout.setVerticalGroup(
                jPanelLeftTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanelLeftTopLayout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, Short.MAX_VALUE)
                                .addGap(8, 8, 8))
        );

        jButtonSideIndex.setBackground(new java.awt.Color(34, 41, 71));
        jButtonSideIndex.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButtonSideIndex.setForeground(new java.awt.Color(255, 153, 153));
        jButtonSideIndex.setText("Index");
        jButtonSideIndex.setToolTipText(null);
        jButtonSideIndex.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButtonSideIndex.setBorderPainted(false);
        jButtonSideIndex.setContentAreaFilled(false);
        jButtonSideIndex.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonSideIndex.setMaximumSize(new java.awt.Dimension(39, 22));
        jButtonSideIndex.setMinimumSize(new java.awt.Dimension(39, 22));
        jButtonSideIndex.setOpaque(true);
        jButtonSideIndex.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseEntered(java.awt.event.MouseEvent evt)
            {
                jButtonSideIndexMouseEntered(evt);
            }

            public void mouseExited(java.awt.event.MouseEvent evt)
            {
                jButtonSideIndexMouseExited(evt);
            }
        });
        jButtonSideIndex.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButtonSideIndexActionPerformed(evt);
            }
        });

        jButtonSideSearch.setBackground(new java.awt.Color(34, 41, 71));
        jButtonSideSearch.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButtonSideSearch.setForeground(new java.awt.Color(255, 255, 255));
        jButtonSideSearch.setText("Search");
        jButtonSideSearch.setToolTipText(null);
        jButtonSideSearch.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButtonSideSearch.setBorderPainted(false);
        jButtonSideSearch.setContentAreaFilled(false);
        jButtonSideSearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonSideSearch.setMaximumSize(new java.awt.Dimension(39, 22));
        jButtonSideSearch.setMinimumSize(new java.awt.Dimension(39, 22));
        jButtonSideSearch.setOpaque(true);
        jButtonSideSearch.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseEntered(java.awt.event.MouseEvent evt)
            {
                jButtonSideSearchMouseEntered(evt);
            }

            public void mouseExited(java.awt.event.MouseEvent evt)
            {
                jButtonSideSearchMouseExited(evt);
            }
        });
        jButtonSideSearch.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButtonSideSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelLeftSideLayout = new javax.swing.GroupLayout(jPanelLeftSide);
        jPanelLeftSide.setLayout(jPanelLeftSideLayout);
        jPanelLeftSideLayout.setHorizontalGroup(
                jPanelLeftSideLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanelLeftTop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonSideIndex, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonSideSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanelLeftSideLayout.setVerticalGroup(
                jPanelLeftSideLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanelLeftSideLayout.createSequentialGroup()
                                .addComponent(jPanelLeftTop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonSideIndex, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(1, 1, 1)
                                .addComponent(jButtonSideSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanelMainLayout = new javax.swing.GroupLayout(jPanelMain);
        jPanelMain.setLayout(jPanelMainLayout);
        jPanelMainLayout.setHorizontalGroup(
                jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanelMainLayout.createSequentialGroup()
                                .addComponent(jPanelLeftSide, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLayeredPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 680, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanelMainLayout.setVerticalGroup(
                jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanelLeftSide, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLayeredPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 543, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanelMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanelMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>

    private void jButtonBrowseOutputActionPerformed(java.awt.event.ActionEvent evt)
    {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("Select Output folder");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        // Disable the "All files" option
        chooser.setAcceptAllFileFilterUsed(false);

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
        {
            String path = chooser.getSelectedFile().toString();
            jTextFieldOutput.setText(path);
            Indexer.outputFolder = path;

        }
        else
        {
            jTextFieldOutput.setText("");
            Indexer.outputFolder = "";
        }
    }

    private void jButtonBrowseDocsActionPerformed(java.awt.event.ActionEvent evt)
    {

        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("Select Document folder");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        // Disable the "All files" option
        chooser.setAcceptAllFileFilterUsed(false);

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
        {
            String path = chooser.getSelectedFile().toString();
            jTextFieldDocs.setText(path);
            Indexer.documentFolder = path;
        }
        else
        {
            jTextFieldDocs.setText("");
            Indexer.documentFolder = "";
        }
    }


    private void openFile(String filePath)
    {
        File f = new File(filePath);
        try
        {
            Runtime.getRuntime().exec("explorer.exe " + f.getAbsolutePath());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    e.getClass() + "\n" + e.getMessage(),
                    "Directory Exception",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addDocumentResults(int number, float score, String fileName, String path,  String content)
    {
        JPanel jp = new JPanel();
        jp.setPreferredSize(new Dimension(572, 75));
        jp.setMaximumSize(new Dimension(572, 75));
        jp.setBackground(new Color(34, 41, 71));

        jp.setLayout(null);

        JLabel scoreLabel = new JLabel(String.format("Score: %.4f", score));
        scoreLabel.setFont(new Font("Takahoma", Font.PLAIN, 13));
        scoreLabel.setForeground(Color.CYAN);
        jp.add(scoreLabel);
        scoreLabel.setBounds(484, 50, 150, 20);

        JLabel docNum = new JLabel(String.valueOf(number));
        docNum.setFont(new Font("Takahoma", Font.BOLD, 18));
        docNum.setForeground(Color.white);
        jp.add(docNum);
        docNum.setBounds(20, 28, 80, 20);

        JLabel docID = new JLabel(fileName);
        docID.setFont(new Font("Takahoma", Font.PLAIN, 13));
        docID.setForeground(new Color(3, 255, 150));
        jp.add(docID);
        docID.setBounds(50, -2, 200, 30);

        JLabel docPath = new JLabel(path);
        docPath.setFont(new Font("Takahoma", Font.PLAIN, 13));
        docPath.setForeground(new Color(255, 153, 153));
        jp.add(docPath);
        docPath.setBounds(150, -2, 420, 30);

        JLabel docContent = new JLabel(content);
        docContent.setFont(new Font("Takahoma", Font.PLAIN, 14));
        docContent.setForeground(Color.white);
        jp.add(docContent);
        docContent.setBounds(50, 20, 500, 30);

        jp.setToolTipText(path);
        jp.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jPanel1.setLayout(new BoxLayout(jPanel1, BoxLayout.Y_AXIS));
        jPanel1.add(Box.createRigidArea(new Dimension(20, 5)));
        jPanel1.add(jp);

        jScrollPaneAnswers.revalidate();
        jScrollPaneAnswers.repaint();

        // Add mouse listeners to panels
        jp.addMouseListener(new MouseListener()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                openFile(path);
            }

            @Override
            public void mouseEntered(MouseEvent e)
            {

            }

            @Override
            public void mouseExited(MouseEvent e)
            {

            }

            @Override
            public void mousePressed(MouseEvent e)
            {

            }

            @Override
            public void mouseReleased(MouseEvent e)
            {

            }

        });

    }

    private void jButtonSearchActionPerformed(java.awt.event.ActionEvent evt)
    {
        if (jTextFieldQuery.getText().equals("")) return;

        // Disable button
        jButtonSearch.setEnabled(false);

        // Remove all previous search results
        jPanel1.removeAll();

        // Start counting time
        var startTime = currentTimeMillis();

        jLabelQueryTime.setText(" Searching...");

        // Query search
        var docs = Search.search(jTextFieldQuery.getText());

        // Print the time elapsed in seconds
        float elapsedTime = (float) ((currentTimeMillis() - startTime) / 1000.0);
        jLabelQueryTime.setText(" Query time: " + elapsedTime + " seconds");

        if (docs != null)
        {
            int docNum = 1;
            for (String doc : docs.keySet())
            {
                String path = Search.currentQueryFilePaths.get(doc);
                addDocumentResults(docNum++, docs.get(doc), doc, path, "Edw dn kserw akoma ti na valw. hehehehe");
            }
        }

        // Enable button
        jButtonSearch.setEnabled(true);
    }

    private void jButtonSideIndexActionPerformed(java.awt.event.ActionEvent evt)
    {
        jLayeredPane3.removeAll();
        jLayeredPane3.add(jPanelIndex);
        jLayeredPane3.repaint();
        jLayeredPane3.revalidate();
        jButtonSideSearch.setForeground(new Color(255, 255, 255));
        jButtonSideIndex.setForeground(new Color(255, 153, 153));
    }

    private void jButtonSideSearchActionPerformed(java.awt.event.ActionEvent evt)
    {
        jLayeredPane3.removeAll();
        jLayeredPane3.add(jPanelSearch);
        jLayeredPane3.repaint();
        jLayeredPane3.revalidate();
        jButtonSideIndex.setForeground(new Color(255, 255, 255));
        jButtonSideSearch.setForeground(new Color(255, 153, 153));

        // Change default path to output
        String path = MainForm.instance.jTextFieldOutput.getText();
        if (!MainForm.instance.jTextFieldOutput.getText().equals(""))
        {
            Search.indexDirectory = path;
        }
        else
        {
            Search.indexDirectory = "CollectionIndex";
        }

        // Load vocabulary and calculate document number
        if (Search.vocabulary.size() == 0)
        {
            jLabelQueryTime.setText(" Loading vocabulary...");
            Thread thread = new Thread()
            {
              public void run()
              {
                  Search.Initialize();
                  if (Search.vocabulary.size() > 0)
                  {
                      jLabelQueryTime.setText(" Vocabulary loaded! Ready for search!");
                      jButtonSearch.setEnabled(true);
                  }
                  else
                  {
                      jLabelQueryTime.setText(" There was an error loading vocabulary.");
                  }


              }
            };

            thread.start();


        }

    }

    private void jButtonSideIndexMouseEntered(java.awt.event.MouseEvent evt)
    {
        jButtonSideIndex.setBackground(new Color(61, 74, 128));
        // Default color: [34,41,71]
    }

    private void jButtonSideSearchMouseEntered(java.awt.event.MouseEvent evt)
    {
        jButtonSideSearch.setBackground(new Color(61, 74, 128));
    }

    private void jButtonSideSearchMouseExited(java.awt.event.MouseEvent evt)
    {
        jButtonSideSearch.setBackground(new Color(34, 41, 71));
    }

    private void jButtonSideIndexMouseExited(java.awt.event.MouseEvent evt)
    {
        jButtonSideIndex.setBackground(new Color(34, 41, 71));
    }

    private void jButtonBrowseDocsMouseEntered(java.awt.event.MouseEvent evt)
    {
        jButtonBrowseDocs.setBackground(new Color(61, 74, 128));
    }

    private void jButtonBrowseDocsMouseExited(java.awt.event.MouseEvent evt)
    {
        jButtonBrowseDocs.setBackground(new Color(34, 41, 71));
    }

    private void jButtonBrowseOutputMouseEntered(java.awt.event.MouseEvent evt)
    {
        jButtonBrowseOutput.setBackground(new Color(61, 74, 128));
    }

    private void jButtonBrowseOutputMouseExited(java.awt.event.MouseEvent evt)
    {
        jButtonBrowseOutput.setBackground(new Color(34, 41, 71));
    }

    private void jButtonIndexMouseEntered(java.awt.event.MouseEvent evt)
    {
        jButtonIndex.setBackground(new Color(61, 74, 128));
    }

    private void jButtonIndexMouseExited(java.awt.event.MouseEvent evt)
    {
        jButtonIndex.setBackground(new Color(34, 41, 71));
    }

    private void jButtonSearchMouseEntered(java.awt.event.MouseEvent evt)
    {
        jButtonSearch.setBackground(new Color(61, 74, 128));
    }

    private void jButtonSearchMouseExited(java.awt.event.MouseEvent evt)
    {
        jButtonSearch.setBackground(new Color(34, 41, 71));
    }

    private void jButtonIndexActionPerformed(java.awt.event.ActionEvent evt)
    {
        try
        {
            Indexer.MEMORY_LIMIT = Integer.parseInt(jTextFieldMemoryLimit.getText());
        }
        catch(NumberFormatException e)
        {
            Indexer.MEMORY_LIMIT = 0;
        }

        jTextAreaIndexInfo.setText("");
        jProgressBarIndex.setValue(0);

        Thread thread = new Thread()
        {
            public void run()
            {
                try
                {
                    Indexer.main(null);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null,
                            e.getClass() + "\n" + e.getMessage(),
                            "Directory Exception",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        };

        thread.start();
    }

    public static void main(String[] args)
    {

        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                instance = new MainForm();
                instance.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify
    public static MainForm instance;
    private javax.swing.JLabel JLabelDocNum;
    private javax.swing.JButton jButtonBrowseDocs;
    private javax.swing.JButton jButtonBrowseOutput;
    private javax.swing.JButton jButtonIndex;
    private javax.swing.JButton jButtonSearch;
    private javax.swing.JButton jButtonSideIndex;
    private javax.swing.JButton jButtonSideSearch;
    private javax.swing.JComboBox<String> jComboBoxType;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLayeredPane jLayeredPane3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelIndex;
    private javax.swing.JPanel jPanelLeftSide;
    private javax.swing.JPanel jPanelLeftTop;
    private javax.swing.JPanel jPanelMain;
    private javax.swing.JPanel jPanelSearch;
    private javax.swing.JPanel jPanelSnippet;
    public javax.swing.JProgressBar jProgressBarIndex;
    private javax.swing.JScrollPane jScrollPaneAnswers;
    private javax.swing.JScrollPane jScrollPaneInfo;
    public javax.swing.JTextArea jTextAreaIndexInfo;
    private javax.swing.JTextField jTextFieldDocs;
    private javax.swing.JTextField jTextFieldMemoryLimit;
    public javax.swing.JTextField jTextFieldOutput;
    private javax.swing.JTextField jTextFieldQuery;
    private javax.swing.JLabel jTopLabel1;
    private javax.swing.JLabel jTopLabel2;
    private javax.swing.JLabel jLabelQueryTime;

    // End of variables declaration
}
