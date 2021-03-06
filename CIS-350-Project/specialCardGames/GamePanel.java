package specialCardGames;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**********************************************************************
 * This class creates the panels the panels that will be placed on
 * the JFrame to show on the screen when the game is started. This is
 * the view of the program.
 *
 *  @author Jarod Collier, Maz Ashgar, Josh Lowell
 *  @version 9/28/2018
 *********************************************************************/
public class GamePanel extends JPanel implements ActionListener {

    /** variable to hold the background image */
    private Image backGround = null;

    /** Labels for all of the different cards */
    private JLabel dealerLabel, redBlackLabel, highLowLabel,
            inOutLabel, suitLabel, randomLabel, pointsLabel,
            questionLabel;

    /** First stage ImageIcons for all of the cards */
    private ImageIcon dealerCard, redBlackCard, highLowCard,
            inOutCard, suitCard, randomCard;

    /** Second Stage ImageIcons */
    private ImageIcon faceDownCard, firstColumn,
            secondColumn1, secondColumn2,
            thirdColumn1, thirdColumn2, thirdColumn3,
            fourthColumn1, fourthColumn2, fourthColumn3, fourthColumn4,
            fifthColumn1, fifthColumn2, fifthColumn3,
            sixthColumn1, sixthColumn2,
            seventhColumn;

    /** Creates the color buttons */
    private JButton redButton, blackButton;

    /** Creates the highLow buttons */
    private JButton higherButton, lowerButton, hiLowEqualButton;

    /** Creates the inOutButtons */
    private JButton insideButton, outsideButton, inOutEqualButton;

    /** Creates the suits buttons */
    private JButton spadesButton, heartsButton, clubsButton,
            diamondsButton;

    /** Creates the buttons for the numbers */
    private JButton twoButton, threeButton, fourButton, fiveButton,
            sixButton, sevenButton, eightButton, nineButton, tenButton,
            jackButton, queenButton, kingButton, aceButton;

    /** First stage JButtons */
    private JButton[] colors, highLow, inOut, suits, numbers;

    /** Second Stage Cards */
    private JLabel[] blankCards, cards;

    /** Second Stage buttons of which path to take */
    private JButton flip, chosenPathHigh1, chosenPathHigh2,
            chosenPathHigh3, chosenPathHigh4, chosenPathHigh5,
            chosenPathHigh6, chosenPathLow1, chosenPathLow2,
            chosenPathLow3, chosenPathLow4, chosenPathLow5,
            chosenPathLow6;

    /** Button array that holds all of the Second Stage Buttons */
    private JButton[] upPath, downPath;

    /** objects of both stages of the game */
    private GameLogic stage1, stage2;

    /** different panels to be placed on the JFrame */
    private JPanel firstStage, secondStage, pointsPanel, questionPanel;

    /** Booleans for the Second Stage to determine the current column */
    private boolean secondCol1, secondCol2,
            thirdCol1, thirdCol2, thirdCol3,
            fourthCol1, fourthCol2, fourthCol3, fourthCol4,
            fifthCol1, fifthCol2, fifthCol3,
            sixthCol1, sixthCol2,
            seventhCol;

    /** Keeps track of cards used for second stage */
    private int placeInDeck;

    /** Final variables for first stage */
    private final int BUTTONS_X_VALUE = 1, BUTTONS_Y_VALUE = 2,
            CARDS_X_VALUE = 0, CARDS_Y_VALUE = 1;

    /** Final variables for the second stage */
    private final int X_CARD_POSITION = 1, Y_CARD_POSITION = 0,
            FIRST_COLUMN = 0, SECOND_COLUMN_1 = 1, SECOND_COLUMN_2 = 2,
            THIRD_COLUMN_1 = 3, THIRD_COLUMN_2 = 4, THIRD_COLUMN_3 = 5,
            FOURTH_COLUMN_1 = 6, FOURTH_COLUMN_2 = 7,
            FOURTH_COLUMN_3 = 8, FOURTH_COLUMN_4 = 9,
            FIFTH_COLUMN_1 = 10, FIFTH_COLUMN_2 = 11,
            FIFTH_COLUMN_3 = 12, SIXTH_COLUMN_1 = 13,
            SIXTH_COLUMN_2 = 14, SEVENTH_COLUMN = 15;


    /******************************************************************
     * default Constructor for the game panel.
     *****************************************************************/
    public GamePanel() {

        //for both stages, call the constructor of the game logic
        stage1 = new GameLogic();
        stage2 = new GameLogic();

        //get the width of the screen from the operating system
        int screenWidth = Toolkit.getDefaultToolkit().
                getScreenSize().width;

        //get the height of the screen from the operating system
        int screenHeight = Toolkit.getDefaultToolkit().
                getScreenSize().height;

        //set preferred size to the size of the screen
        setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());

        // Creates the buttons and Labels for stage One
        stageOneButtonsAndLabels();

        //initialize the points label
        pointsLabel = new JLabel("Players points: "
                + stage1.getScore());
        pointsLabel.setFont(new Font("Cooper Black",
                Font.BOLD, 30));

        //initialize the question label
        questionLabel = new JLabel("What's the color of the card?");
        questionLabel.setFont(new Font("Cooper Black",
                Font.BOLD, 40));

        // Create one JPanel for the first stage of the game
        firstStage = new JPanel(new GridBagLayout());
        firstStage.setPreferredSize(new Dimension(screenWidth,
                screenHeight / 2));

        // Create one JPanel for the second stage of the game
        secondStage = new JPanel(new GridBagLayout());
        secondStage.setPreferredSize(new Dimension(screenWidth,
                screenHeight - 200));

        // Sets layout of how cards are set
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipadx = 8;

        // Adding the cards of First Stage
        placingCards(c);

        // Question buttons for first stage
        placingQuestionButtons(c);

        // Second Stage creations
        secondStageCreations();

        // placing the cards
        secondStagePlacement(c);

        // Adding the points label to the points panel
        pointsPanel = new JPanel();
        pointsPanel.setPreferredSize(new Dimension(screenWidth,
                screenHeight / 8));
        pointsPanel.add(pointsLabel);

        // Adding the question label to the question panel
        questionPanel = new JPanel();
        questionPanel.add(questionLabel);

        // Add the question label to the main panel
        add(questionPanel, BorderLayout.PAGE_START);

        // Add the cards panel to the main panel
        add(firstStage, BorderLayout.CENTER);

        // Add the second stage to the main panel
        add(secondStage, BorderLayout.CENTER);

        // Add the points label to the main panel
        add(pointsPanel, BorderLayout.PAGE_END);

        //make all the panels transparent
        firstStage.setOpaque(false);
        questionPanel.setOpaque(false);
        pointsPanel.setOpaque(false);
        secondStage.setOpaque(false);
        secondStage.setVisible(false);

        //get the image from the directory
        changeImage();

        //set the size of the superPanel
        setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
    }


    /******************************************************************
     * This method sets the panel layout for stage two of the game.
     * By placing all the cards and the buttons in their correct spots
     *
     * @param c The GridBagConstrains used in creating the first stage
     *****************************************************************/
    private void secondStagePlacement(GridBagConstraints c) {

        // Start placing the cards on the panel, diamond shaped
        // column 1
        c.gridwidth = 1;
        c.gridheight = 4;
        c.ipady = 8;
        c.gridx = X_CARD_POSITION;
        c.gridy = Y_CARD_POSITION;
        secondStage.add(blankCards[FIRST_COLUMN], c);
        secondStage.add(cards[FIRST_COLUMN], c);

        // Column 2, card 1
        c.gridheight = 3;
        c.gridx = X_CARD_POSITION + 1;
        c.gridy = Y_CARD_POSITION;
        secondStage.add(blankCards[SECOND_COLUMN_1], c);
        secondStage.add(cards[SECOND_COLUMN_1], c);

        // Column 2, card 2
        c.gridy = Y_CARD_POSITION + 1;
        secondStage.add(blankCards[SECOND_COLUMN_2], c);
        secondStage.add(cards[SECOND_COLUMN_2], c);

        // Column 3, card 1
        c.gridheight = 2;
        c.gridx = X_CARD_POSITION + 2;
        c.gridy = Y_CARD_POSITION;
        secondStage.add(blankCards[THIRD_COLUMN_1], c);
        secondStage.add(cards[THIRD_COLUMN_1], c);

        // Column 3, card 2
        c.gridy = Y_CARD_POSITION + 1;
        secondStage.add(blankCards[THIRD_COLUMN_2], c);
        secondStage.add(cards[THIRD_COLUMN_2], c);

        // Column 3, card 3
        c.gridy = Y_CARD_POSITION + 2;
        secondStage.add(blankCards[THIRD_COLUMN_3], c);
        secondStage.add(cards[THIRD_COLUMN_3], c);

        // Column 4, card 1
        c.gridheight = 1;
        c.gridx = X_CARD_POSITION + 3;
        c.gridy = Y_CARD_POSITION;
        secondStage.add(blankCards[FOURTH_COLUMN_1], c);
        secondStage.add(cards[FOURTH_COLUMN_1], c);

        // Column 4, card 2
        c.gridy = Y_CARD_POSITION + 1;
        secondStage.add(blankCards[FOURTH_COLUMN_2], c);
        secondStage.add(cards[FOURTH_COLUMN_2], c);

        // Column 4, card 3
        c.gridy = Y_CARD_POSITION + 2;
        secondStage.add(blankCards[FOURTH_COLUMN_3], c);
        secondStage.add(cards[FOURTH_COLUMN_3], c);

        // Column 4, card 4
        c.gridy = Y_CARD_POSITION + 3;
        secondStage.add(blankCards[FOURTH_COLUMN_4], c);
        secondStage.add(cards[FOURTH_COLUMN_4], c);

        // Column 5, card 1
        c.gridheight = 2;
        c.gridx = X_CARD_POSITION + 4;
        c.gridy = Y_CARD_POSITION;
        secondStage.add(blankCards[FIFTH_COLUMN_1], c);
        secondStage.add(cards[FIFTH_COLUMN_1], c);

        // Column 5, card 2
        c.gridy = Y_CARD_POSITION + 1;
        secondStage.add(blankCards[FIFTH_COLUMN_2], c);
        secondStage.add(cards[FIFTH_COLUMN_2], c);

        // Column 5, card 3
        c.gridy = Y_CARD_POSITION + 2;
        secondStage.add(blankCards[FIFTH_COLUMN_3], c);
        secondStage.add(cards[FIFTH_COLUMN_3], c);

        // Column 6, card 1
        c.gridheight = 3;
        c.gridx = X_CARD_POSITION + 5;
        c.gridy = Y_CARD_POSITION;
        secondStage.add(blankCards[SIXTH_COLUMN_1], c);
        secondStage.add(cards[SIXTH_COLUMN_1], c);

        // Column 6, card 2
        c.gridy = Y_CARD_POSITION + 1;
        secondStage.add(blankCards[SIXTH_COLUMN_2], c);
        secondStage.add(cards[SIXTH_COLUMN_2], c);

        // Column 7, card 1
        c.gridheight = 4;
        c.gridx = X_CARD_POSITION + 6;
        c.gridy = Y_CARD_POSITION;
        secondStage.add(blankCards[SEVENTH_COLUMN], c);
        secondStage.add(cards[SEVENTH_COLUMN], c);


        // Adding the flip button
        c.gridx = X_CARD_POSITION;
        c.gridy = Y_CARD_POSITION + 4;
        secondStage.add(flip, c);

        // Adding the path buttons first column
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = X_CARD_POSITION + 1;
        c.gridy = Y_CARD_POSITION + 4;
        secondStage.add(chosenPathHigh1, c);
        chosenPathHigh1.setVisible(false);

        c.gridy = Y_CARD_POSITION + 5;
        secondStage.add(chosenPathLow1, c);
        chosenPathLow1.setVisible(false);

        // Second Column
        c.gridx = X_CARD_POSITION + 2;
        c.gridy = Y_CARD_POSITION + 4;
        secondStage.add(chosenPathHigh2, c);
        chosenPathHigh2.setVisible(false);

        c.gridy = Y_CARD_POSITION + 5;
        secondStage.add(chosenPathLow2, c);
        chosenPathLow2.setVisible(false);

        // Third Column
        c.gridx = X_CARD_POSITION + 3;
        c.gridy = Y_CARD_POSITION + 4;
        secondStage.add(chosenPathHigh3, c);
        chosenPathHigh3.setVisible(false);

        c.gridy = Y_CARD_POSITION + 5;
        secondStage.add(chosenPathLow3, c);
        chosenPathLow3.setVisible(false);

        // Fourth Column
        c.gridx = X_CARD_POSITION + 4;
        c.gridy = Y_CARD_POSITION + 4;
        secondStage.add(chosenPathHigh4, c);
        chosenPathHigh4.setVisible(false);

        c.gridy = Y_CARD_POSITION + 5;
        secondStage.add(chosenPathLow4, c);
        chosenPathLow4.setVisible(false);

        // Fifth Column
        c.gridx = X_CARD_POSITION + 5;
        c.gridy = Y_CARD_POSITION + 4;
        secondStage.add(chosenPathHigh5, c);
        chosenPathHigh5.setVisible(false);

        c.gridy = Y_CARD_POSITION + 5;
        secondStage.add(chosenPathLow5, c);
        chosenPathLow5.setVisible(false);

        // Sixth Column
        c.gridx = X_CARD_POSITION + 6;
        c.gridy = Y_CARD_POSITION + 4;
        secondStage.add(chosenPathHigh6, c);
        chosenPathHigh6.setVisible(false);

        c.gridy = Y_CARD_POSITION + 5;
        secondStage.add(chosenPathLow6, c);
        chosenPathLow6.setVisible(false);
    }

    /******************************************************************
     * This method creats the second stage of the game, in which it
     * resets the deck of cards, places the cards and
     * places the button on the screen.
     *****************************************************************/
    private void secondStageCreations() {

        // Creating the blank cards
        blankCards = new JLabel[16];
        faceDownCard = new ImageIcon(getClass().
                getResource("cardImages/small/BackSmall.png"));
        for (int blankCard = 0; blankCard < blankCards.length;
             blankCard++){
            blankCards[blankCard] = new JLabel(faceDownCard);
        }

        // Creating the cards
        try {
            firstColumn = new ImageIcon(getClass().
                    getResource(stage2.smallCardString(
                            FIRST_COLUMN + 1)));
            secondColumn1 = new ImageIcon(getClass().
                    getResource(stage2.smallCardString(
                            SECOND_COLUMN_1 + 1)));
            secondColumn2 = new ImageIcon(getClass().
                    getResource(stage2.smallCardString(
                            SECOND_COLUMN_2 + 1)));
            thirdColumn1 = new ImageIcon(getClass().
                    getResource(stage2.smallCardString(
                            THIRD_COLUMN_1 + 1)));
            thirdColumn2 = new ImageIcon(getClass().
                    getResource(stage2.smallCardString(
                            THIRD_COLUMN_2 + 1)));
            thirdColumn3 = new ImageIcon(getClass().
                    getResource(stage2.smallCardString(
                            THIRD_COLUMN_3 + 1)));
            fourthColumn1 = new ImageIcon(getClass().
                    getResource(stage2.smallCardString(
                            FOURTH_COLUMN_1 + 1)));
            fourthColumn2 = new ImageIcon(getClass().
                    getResource(stage2.smallCardString(
                            FOURTH_COLUMN_2 + 1)));
            fourthColumn3 = new ImageIcon(getClass().
                    getResource(stage2.smallCardString(
                            FOURTH_COLUMN_3 + 1)));
            fourthColumn4 = new ImageIcon(getClass().
                    getResource(stage2.smallCardString(
                            FOURTH_COLUMN_4 + 1)));
            fifthColumn1 = new ImageIcon(getClass().
                    getResource(stage2.smallCardString(
                            FIFTH_COLUMN_1 + 1)));
            fifthColumn2 = new ImageIcon(getClass().
                    getResource(stage2.smallCardString(
                            FIFTH_COLUMN_2 + 1)));
            fifthColumn3 = new ImageIcon(getClass().
                    getResource(stage2.smallCardString(
                            FIFTH_COLUMN_3 + 1)));
            sixthColumn1 = new ImageIcon(getClass().
                    getResource(stage2.smallCardString(
                            SIXTH_COLUMN_1 + 1)));
            sixthColumn2 = new ImageIcon(getClass().
                    getResource(stage2.smallCardString(
                            SIXTH_COLUMN_2 + 1)));
            seventhColumn = new ImageIcon(getClass().
                    getResource(stage2.smallCardString(
                            SEVENTH_COLUMN + 1)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Creates int to be the next card in the deck after
        // each card has been used
        placeInDeck = SEVENTH_COLUMN + 2;

        /* Second Stage array of imageIcons */
        ImageIcon[] cardLocation = new ImageIcon[16];
        cardLocation[0] = firstColumn;
        cardLocation[1] = secondColumn1;
        cardLocation[2] = secondColumn2;
        cardLocation[3] = thirdColumn1;
        cardLocation[4] = thirdColumn2;
        cardLocation[5] = thirdColumn3;
        cardLocation[6] = fourthColumn1;
        cardLocation[7] = fourthColumn2;
        cardLocation[8] = fourthColumn3;
        cardLocation[9] = fourthColumn4;
        cardLocation[10] = fifthColumn1;
        cardLocation[11] = fifthColumn2;
        cardLocation[12] = fifthColumn3;
        cardLocation[13] = sixthColumn1;
        cardLocation[14] = sixthColumn2;
        cardLocation[15] = seventhColumn;

        // Array to hold the cards
        cards = new JLabel[16];

        // Creating the labels of the cards
        for (int card = 0; card < cards.length; card++) {
            cards[card] = new JLabel(cardLocation[card]);
        }

        // Arrays to hold the path buttons
        upPath = new JButton[6];
        downPath = new JButton[6];

        // Create up path buttons and add to the array
        chosenPathHigh1 = new JButton("Go High");
        chosenPathHigh2 = new JButton("Go High");
        chosenPathHigh3 = new JButton("Go High");
        chosenPathHigh4 = new JButton("Go High");
        chosenPathHigh5 = new JButton("Go High");
        chosenPathHigh6 = new JButton("Go High");
        upPath[0] = chosenPathHigh1;
        upPath[1] = chosenPathHigh2;
        upPath[2] = chosenPathHigh3;
        upPath[3] = chosenPathHigh4;
        upPath[4] = chosenPathHigh5;
        upPath[5] = chosenPathHigh6;

        // Create down path buttons and add to the array
        chosenPathLow1 = new JButton("Go Low");
        chosenPathLow2 = new JButton("Go Low");
        chosenPathLow3 = new JButton("Go Low");
        chosenPathLow4 = new JButton("Go Low");
        chosenPathLow5 = new JButton("Go Low");
        chosenPathLow6 = new JButton("Go Low");
        downPath[0] = chosenPathLow1;
        downPath[1] = chosenPathLow2;
        downPath[2] = chosenPathLow3;
        downPath[3] = chosenPathLow4;
        downPath[4] = chosenPathLow5;
        downPath[5] = chosenPathLow6;

        // Create up path actionListeners
        for (JButton upPath : upPath) {
            upPath.addActionListener(this);
        }

        // Create down path actionListeners
        for (JButton downPath : downPath) {
            downPath.addActionListener(this);
        }

        // Create the Flip button
        flip = new JButton("Flip Card");
        flip.addActionListener(this);

        // Start with booleans as false for second stage paths
        secondCol1 = false;
        secondCol2 = false;
        thirdCol1 = false;
        thirdCol2 = false;
        thirdCol3 = false;
        fourthCol1 = false;
        fourthCol2 = false;
        fourthCol3 = false;
        fourthCol4 = false;
        fifthCol1 = false;
        fifthCol2 = false;
        fifthCol3 = false;
        sixthCol1 = false;
        sixthCol2 = false;
        seventhCol = false;
    }

    /******************************************************************
     * This method creates the first stage of the game, in which it
     * places the buttons and the cards on the screen.
     *****************************************************************/
    private void stageOneButtonsAndLabels() {

        // Arrays for different buttons
        colors = new JButton[2];
        highLow = new JButton[3];
        inOut = new JButton[3];
        suits = new JButton[4];
        numbers = new JButton[13];

        try {
            //get Card images for display
            dealerCard = new ImageIcon(getClass().
                    getResource("cardImages/big/BackBig.png"));
            redBlackCard = new ImageIcon(getClass().
                    getResource(stage1.bigCardString(1)));
            highLowCard = new ImageIcon(getClass().
                    getResource(stage1.bigCardString(2)));
            inOutCard = new ImageIcon(getClass().
                    getResource(stage1.bigCardString(3)));
            suitCard = new ImageIcon(getClass().
                    getResource(stage1.bigCardString(4)));
            randomCard = new ImageIcon(getClass().
                    getResource(stage1.bigCardString(5)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //create cards for the cards panel
        dealerLabel = new JLabel(dealerCard);
        redBlackLabel = new JLabel(redBlackCard);
        highLowLabel = new JLabel(highLowCard);
        inOutLabel = new JLabel(inOutCard);
        suitLabel = new JLabel(suitCard);
        randomLabel = new JLabel(randomCard);

        //resize the label to fit the card images
        dealerLabel.setSize(dealerCard.getIconWidth(),
                dealerCard.getIconHeight());
        redBlackLabel.setSize(redBlackCard.getIconWidth(),
                redBlackCard.getIconHeight());
        highLowLabel.setSize(highLowCard.getIconWidth(),
                highLowCard.getIconHeight());
        inOutLabel.setSize(inOutCard.getIconWidth(),
                inOutCard.getIconHeight());
        suitLabel.setSize(suitCard.getIconWidth(),
                suitCard.getIconHeight());
        randomLabel.setSize(randomCard.getIconWidth(),
                randomCard.getIconHeight());

        // Sets the cards to be invisible
        redBlackLabel.setVisible(true);
        highLowLabel.setVisible(true);
        inOutLabel.setVisible(true);
        suitLabel.setVisible(true);
        randomLabel.setVisible(true);

        // initialize the color buttons
        redButton = new JButton("Red");
        blackButton = new JButton("Black");
        colors[0] = redButton;
        colors[1] = blackButton;

        // Initialize the highLow buttons
        higherButton = new JButton("Higher");
        lowerButton = new JButton("Lower");
        hiLowEqualButton = new JButton("Equal");
        highLow[0] = higherButton;
        highLow[1] = lowerButton;
        highLow[2] = hiLowEqualButton;

        // Initialize the inOut buttons
        insideButton = new JButton("Inside");
        outsideButton = new JButton("Outside");
        inOutEqualButton = new JButton("Equal");
        inOut[0] = insideButton;
        inOut[1] = outsideButton;
        inOut[2] = inOutEqualButton;

        // Initialize the suit buttons
        spadesButton = new JButton("Spades");
        heartsButton = new JButton("Hearts");
        clubsButton = new JButton("Clubs");
        diamondsButton = new JButton("Diamonds");
        suits[0] = spadesButton;
        suits[1] = heartsButton;
        suits[2] = clubsButton;
        suits[3] = diamondsButton;

        // Initialize the number buttons
        twoButton = new JButton("Two");
        threeButton = new JButton("Three");
        fourButton = new JButton("Four");
        fiveButton = new JButton("Five");
        sixButton = new JButton("Six");
        sevenButton = new JButton("Seven");
        eightButton = new JButton("Eight");
        nineButton = new JButton("Nine");
        tenButton = new JButton("Ten");
        jackButton = new JButton("Jack");
        queenButton = new JButton("Queen");
        kingButton = new JButton("King");
        aceButton = new JButton("Ace");
        numbers[0] = twoButton;
        numbers[1] = threeButton;
        numbers[2] = fourButton;
        numbers[3] = fiveButton;
        numbers[4] = sixButton;
        numbers[5] = sevenButton;
        numbers[6] = eightButton;
        numbers[7] = nineButton;
        numbers[8] = tenButton;
        numbers[9] = jackButton;
        numbers[10] = queenButton;
        numbers[11] = kingButton;
        numbers[12] = aceButton;

        // add action listeners to the color buttons
        for (JButton color : colors) {
            color.addActionListener(this);
        }
        // Add action listeners to the highLow buttons
        for (JButton aHighLow : highLow) {
            aHighLow.addActionListener(this);
        }

        // Add action listeners to the inOut buttons
        for (JButton anInOut : inOut) {
            anInOut.addActionListener(this);
        }

        // Add action listeners to the suit buttons
        for (JButton suit : suits) {
            suit.addActionListener(this);
        }

        // Add action listeners to the number buttons
        for (JButton number : numbers) {
            number.addActionListener(this);
        }
    }

    /******************************************************************
     * This is a helper method to place the cards of the first stage
     * of the game.
     *
     * @param c GridBagConstains used to place the cards in the
     *          correct spot
     *****************************************************************/
    private void placingCards(GridBagConstraints c) {

        //The dealer's card, face down
        c.gridx = CARDS_X_VALUE;
        c.gridy = CARDS_Y_VALUE;
        firstStage.add(dealerLabel, c);

        //First questions card
        c.gridx = CARDS_X_VALUE + 1;
        firstStage.add(redBlackLabel, c);
        redBlackLabel.setVisible(false);

        //Second question's card
        c.gridx = CARDS_X_VALUE + 2;
        firstStage.add(highLowLabel, c);
        highLowLabel.setVisible(false);

        //Third question's card
        c.gridx = CARDS_X_VALUE + 3;
        firstStage.add(inOutLabel, c);
        inOutLabel.setVisible(false);

        //Fourth question's card
        c.gridx = CARDS_X_VALUE + 4;
        c.gridwidth = 2;
        firstStage.add(suitLabel, c);
        suitLabel.setVisible(false);

        //Fifth question's card
        c.gridx = CARDS_X_VALUE + 6;
        c.gridwidth = 3;
        firstStage.add(randomLabel, c);
        randomLabel.setVisible(false);
    }

    /******************************************************************
     * This is a helper method to place the buttons for the first stage
     * of the game.
     *
     * @param c GridBagConstains used to place the buttons in the
     *          correct spot
     *****************************************************************/
    private void placingQuestionButtons(GridBagConstraints c) {

        //first question buttons
        c.gridheight = 1;
        c.gridwidth = 1;
        c.gridx = BUTTONS_X_VALUE;
        c.gridy = BUTTONS_Y_VALUE;
        firstStage.add(redButton, c);

        c.gridy = BUTTONS_Y_VALUE + 1;
        firstStage.add(blackButton, c);


        // Second questions buttons
        c.gridx = BUTTONS_X_VALUE + 1;
        c.gridy = BUTTONS_Y_VALUE;
        firstStage.add(higherButton, c);
        higherButton.setVisible(false);

        c.gridy = BUTTONS_Y_VALUE + 1;
        firstStage.add(lowerButton, c);
        lowerButton.setVisible(false);

        c.gridy = BUTTONS_Y_VALUE + 2;
        firstStage.add(hiLowEqualButton, c);
        hiLowEqualButton.setVisible(false);

        // Third questions buttons
        c.gridx = BUTTONS_X_VALUE + 2;
        c.gridy = BUTTONS_Y_VALUE;
        firstStage.add(insideButton, c);
        insideButton.setVisible(false);

        c.gridy = BUTTONS_Y_VALUE + 1;
        firstStage.add(outsideButton, c);
        outsideButton.setVisible(false);

        c.gridy = BUTTONS_Y_VALUE + 2;
        firstStage.add(inOutEqualButton, c);
        inOutEqualButton.setVisible(false);

        // Fourth Questions buttons
        c.gridx = BUTTONS_X_VALUE + 3;
        c.gridy = BUTTONS_Y_VALUE;
        firstStage.add(spadesButton, c);
        spadesButton.setVisible(false);

        c.gridy = BUTTONS_Y_VALUE + 1;
        firstStage.add(heartsButton, c);
        heartsButton.setVisible(false);

        c.gridx = BUTTONS_X_VALUE + 4;
        c.gridy = BUTTONS_Y_VALUE;
        firstStage.add(clubsButton, c);
        clubsButton.setVisible(false);

        c.gridy = BUTTONS_Y_VALUE + 1;
        firstStage.add(diamondsButton, c);
        diamondsButton.setVisible(false);

        // Fifth Questions buttons
        c.gridx = BUTTONS_X_VALUE + 5;
        c.gridy = BUTTONS_Y_VALUE;
        firstStage.add(twoButton, c);
        twoButton.setVisible(false);

        c.gridy = BUTTONS_Y_VALUE + 1;
        firstStage.add(fiveButton, c);
        fiveButton.setVisible(false);

        c.gridy = BUTTONS_Y_VALUE + 2;
        firstStage.add(eightButton, c);
        eightButton.setVisible(false);

        c.gridy = BUTTONS_Y_VALUE + 3;
        firstStage.add(jackButton, c);
        jackButton.setVisible(false);

        c.gridx = BUTTONS_X_VALUE + 6;
        c.gridy = BUTTONS_Y_VALUE;
        firstStage.add(threeButton, c);
        threeButton.setVisible(false);

        c.gridy = BUTTONS_Y_VALUE + 1;
        firstStage.add(sixButton, c);
        sixButton.setVisible(false);

        c.gridy = BUTTONS_Y_VALUE + 2;
        firstStage.add(nineButton, c);
        nineButton.setVisible(false);

        c.gridy = BUTTONS_Y_VALUE + 3;
        firstStage.add(queenButton, c);
        queenButton.setVisible(false);

        c.gridy = BUTTONS_Y_VALUE + 4;
        firstStage.add(aceButton, c);
        aceButton.setVisible(false);

        c.gridx = BUTTONS_X_VALUE + 7;
        c.gridy = BUTTONS_Y_VALUE;
        firstStage.add(fourButton, c);
        fourButton.setVisible(false);

        c.gridy = BUTTONS_Y_VALUE + 1;
        firstStage.add(sevenButton, c);
        sevenButton.setVisible(false);

        c.gridy = BUTTONS_Y_VALUE + 2;
        firstStage.add(tenButton, c);
        tenButton.setVisible(false);

        c.gridy = BUTTONS_Y_VALUE + 3;
        firstStage.add(kingButton, c);
        kingButton.setVisible(false);
    }

    /******************************************************************
     * Overriding the paintComponent method from the Component Class
     * to set the wallpaper of the game.
     * @param g the Graphics object to protect
     *****************************************************************/
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.backGround, 0, 0, null);
    }

    /******************************************************************
     * This method reads the background image from the directory
     * before it displays it on the panel.
     *****************************************************************/
    private void changeImage() {
        try {
            this.backGround = ImageIO.read(getClass().getResource(
                    "bG.jpg"));
            repaint();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Couldn't load background");
        }
    }

    @Override
     /*****************************************************************
     * Creates all of the events whenever a user clicks on something
     * @param e - the event of the user clicking something
     *****************************************************************/
    public void actionPerformed(ActionEvent e) {

        //get the object that initiated the action
        Object choice = e.getSource();

        //if the user is answering the first question (first stage)
        if (choice == redButton || choice == blackButton) {

            // Checks if user clicks on red or black
            clickingColor(choice);

        }
        //if the user is answering the second question (first stage)
        else if (choice == higherButton || choice == lowerButton ||
                choice == hiLowEqualButton) {

            // Checks if user clicked higher or lower
            clickingHighOrLow(choice);

        }
        //if the user is answering the third question (first stage)
        else if (choice == insideButton || choice == outsideButton ||
                choice == inOutEqualButton) {

            // Checks if user clicked in or out
            clickingInOrOut(choice);

        }
        //if the user is answering the fourth question (first stage)
        else if (choice == spadesButton || choice == heartsButton ||
                choice == clubsButton || choice == diamondsButton) {

            // Checks what suit the user picked
            clickingSuit(choice);

        }
        //if the user is answering the fifth question (first stage)
        else if (choice == twoButton || choice == threeButton ||
                choice == fourButton || choice == fiveButton  ||
                choice == sixButton  || choice == sevenButton ||
                choice == eightButton|| choice == nineButton  ||
                choice == tenButton  || choice == jackButton  ||
                choice == queenButton|| choice == kingButton  ||
                choice == aceButton){

            // Checks what number the user picked
            clickingNumber(choice);

        }
        //if the user is answering the first question (second stage)
        else if (choice == flip) {

            // Clicking the flip button
            clickingFlip();

        }
        //if the user says "High" for the first column (second stage)
        else if (choice == chosenPathHigh1) {

            // Clicking higher in the first column
            clickingHigherColumn1();

        }
        //if the user says "low" for the first column (second stage)
        else if (choice == chosenPathLow1) {

            // Clicking lower in the first column
            clickingLowerColumn1();

        }
        //if the user says "High" for the second column (second stage)
        else if (choice == chosenPathHigh2) {

            // Clicking higher in the second column
            clickingHigherColumn2();

        }
        //if the user says "low" for the second column (second stage)
        else if (choice == chosenPathLow2) {

            // Clicking lower in the second column
            clickingLowerColumn2();

        }
        //if the user says "High" for the third column (second stage)
        else if (choice == chosenPathHigh3) {

            // Clicking higher in the third column
            clickingHigherColumn3();

        }
        //if the user says "low" for the third column (second stage)
        else if (choice == chosenPathLow3) {

            // Clicking lower in the third column
            clickingLowerColumn3();

        }
        //if the user says "High" for the fourth column (second stage)
        else if (choice == chosenPathHigh4) {

            // Clicking higher in the fourth column
            clickingHigherColumn4();

        }
        //if the user says "low" for the fourth column (second stage)
        else if (choice == chosenPathLow4) {

            // Clicking lower in the fourth column
            clickingLowerColumn4();

        }
        //if the user says "High" for the fifth column (second stage)
        else if (choice == chosenPathHigh5) {

            // Clicking higher in the fifth column
            clickingHigherColumn5();

        }
        //if the user says "low" for the fifth column (second stage)
        else if (choice == chosenPathLow5) {

            // Clicking lower in the fifth column
            clickingLowerColumn5();

        }
        //if the user says "High" for the sixth column (second stage)
        else if (choice == chosenPathHigh6) {

            // Clicking higher in the sixth column
            clickingHigherColumn6();

        }
        //if the user says "low" for the sixth column (second stage)
        else if (choice == chosenPathLow6) {
            // Clicking lower in the sixth column
            clickingLowerColumn6();
        }

        //update the points label
        if (secondStage.isVisible()) {
            pointsLabel.setText("Players points: "
                    + stage2.getScore());
        } else {
            pointsLabel.setText("Players points: "
                    + stage1.getScore());
        }
    }

    /******************************************************************
     * If the user click the button "flip", reset all the paths,
     * check if the first card is a face card, if yes, tell the user,
     * then reset back to the beginning of the second stage. If not
     * a face a card, then proceed to the first column. Display the
     * buttons and wait for the user to click on one of the buttons.
     *****************************************************************/
    private void clickingFlip() {

        // reset all paths
        secondCol1 = false;
        secondCol2 = false;
        thirdCol1 = false;
        thirdCol2 = false;
        thirdCol3 = false;
        fourthCol1 = false;
        fourthCol2 = false;
        fourthCol3 = false;
        fourthCol4 = false;
        fifthCol1 = false;
        fifthCol2 = false;
        fifthCol3 = false;
        sixthCol1 = false;
        sixthCol2 = false;
        seventhCol = false;

        // Change Card Visibility
        blankCards[FIRST_COLUMN].setVisible(false);

        // Checks for face card
        if (stage2.checkPathFaceCard(firstColumn.toString())) {

            //display a message to the user
            JOptionPane.showMessageDialog(this,
                    "Yikes, face card! Restart!");

            // Cover the face card up
            blankCards[FIRST_COLUMN].setVisible(true);
            flip.setEnabled(true);

            // Change the value of the face card
            firstColumn = new ImageIcon(getClass().getResource(
                    stage2.smallCardString(placeInDeck)));
            cards[FIRST_COLUMN].setIcon(firstColumn);

            // Go to the next card in the deck
            if (placeInDeck <= 52) {
                placeInDeck++;
            } else {
                //if the user used all the 52 cards in the deck,
                //display a message to the user
                JOptionPane.showMessageDialog(this,
                        "You ran out of cards!");
            }
        }

        // If there aren't any face cards, then continue
        else {

            // Change Button Visibility
            flip.setEnabled(false);
            chosenPathHigh1.setVisible(true);
            chosenPathLow1.setVisible(true);
        }
    }

    /******************************************************************
     * If the user clicks on "High" when in column 1,
     * check if the first card is a face card, if yes, tell the user,
     * then reset back to the beginning of the second stage. If not
     * a face a card, then proceed to the second column. Display the
     * buttons and wait for the user to click on one of the buttons.
     *****************************************************************/
    private void clickingHigherColumn1() {


        // Change Card Visibility
        blankCards[SECOND_COLUMN_1].setVisible(false);

        // Checks for face card
        if (stage2.checkPathFaceCard(secondColumn1.toString())) {

            //display a message to the user
            JOptionPane.showMessageDialog(this,
                    "Yikes, face card! Restart!");

            // Cover the face card up
            for (JLabel blankCard : blankCards) {
                blankCard.setVisible(true);
            }
            flip.setEnabled(true);

            // Change the value of the face card
            secondColumn1 = new ImageIcon(getClass().getResource(
                    stage2.smallCardString(placeInDeck)));
            cards[SECOND_COLUMN_1].setIcon(secondColumn1);

            // Go to the next card in the deck
            if (placeInDeck <= 52) {
                placeInDeck++;
            } else {
                //if the user used all the 52 cards in the deck,
                //display a message to the user and end the game
                JOptionPane.showMessageDialog(this,
                        "You ran out of cards! " +
                                "\n\nYour score is" +
                                stage2.getScore() +
                                "\nYour punishment is to do "
                                + stage2.getScore() + " Push-ups");

                System.exit(0);
            }

            // Make buttons disappear
            for (JButton upButtons : upPath) {
                upButtons.setVisible(false);
                upButtons.setEnabled(true);
            }
            for (JButton downButtons : downPath) {
                downButtons.setVisible(false);
                downButtons.setEnabled(true);
            }
        } else {
            //if the user doesn't flip a face card,
            //change Button Visibility
            chosenPathHigh2.setVisible(true);
            chosenPathLow2.setVisible(true);
            chosenPathHigh1.setVisible(false);
            chosenPathLow1.setVisible(false);
            secondCol1 = true;
        }
    }

    /******************************************************************
     * If the user clicks on "Low" when in column 1,
     * check if the first card is a face card, if yes, tell the user,
     * then reset back to the beginning of the second stage. If not
     * a face a card, then proceed to the second column. Display the
     * buttons and wait for the user to click on one of the buttons.
     *****************************************************************/
    private void clickingLowerColumn1() {


        // Change card visibility
        blankCards[SECOND_COLUMN_2].setVisible(false);

        // Checks for face card
        if (stage2.checkPathFaceCard(secondColumn2.toString())) {

            //display a message to the user
            JOptionPane.showMessageDialog(this,
                    "Yikes, face card! Restart!");

            // Cover the face card up
            for (JLabel blankCard : blankCards) {
                blankCard.setVisible(true);
            }
            flip.setEnabled(true);

            // Change the value of the face card
            secondColumn2 = new ImageIcon(getClass().getResource(
                    stage2.smallCardString(placeInDeck)));
            cards[SECOND_COLUMN_2].setIcon(secondColumn2);

            // Go to the next card in the deck
            if (placeInDeck <= 52) {
                placeInDeck++;
            } else {

                //if the user used all the 52 cards in the deck,
                //display a message to the user and end the game
                JOptionPane.showMessageDialog(this,
                        "You ran out of cards! " +
                                "\n\nYour score is" +
                                stage2.getScore() +
                                "\nYour punishment is to do "
                                + stage2.getScore() + " Push-ups");

                System.exit(0);
            }

            // Make buttons disappear
            for (JButton upButtons : upPath) {
                upButtons.setVisible(false);
                upButtons.setEnabled(true);
            }
            for (JButton downButtons : downPath) {
                downButtons.setVisible(false);
                downButtons.setEnabled(true);
            }

        } else {
            //if the user doesn't flip a face card,
            //change Button Visibility
            chosenPathHigh2.setVisible(true);
            chosenPathLow2.setVisible(true);
            chosenPathHigh1.setVisible(false);
            chosenPathLow1.setVisible(false);
            secondCol2 = true;
        }
    }

    /******************************************************************
     * If the user clicks on "High" when in column 2,
     * check if the first card is a face card, if yes, tell the user,
     * then reset back to the beginning of the second stage. If not
     * a face a card, then proceed to the third column. Display the
     * buttons and wait for the user to click on one of the buttons.
     *****************************************************************/
    private void clickingHigherColumn2() {

        //if the user is at the top card in column 2 and clicks high
        if (secondCol1) {

            //change card visibility, and set the paths
            blankCards[THIRD_COLUMN_1].setVisible(false);
            secondCol1 = false;
            thirdCol1 = true;

            // Checks for face card from the preplaced card
            if (stage2.checkPathFaceCard(thirdColumn1.toString())) {

                //display a message to the user
                JOptionPane.showMessageDialog(this,
                        "Yikes, face card! Restart!");

                // Cover the face card up
                for (JLabel blankCard : blankCards) {
                    blankCard.setVisible(true);
                }
                flip.setEnabled(true);

                // Change the value of the face card
                thirdColumn1 = new ImageIcon(getClass().getResource(
                        stage2.smallCardString(placeInDeck)));
                cards[THIRD_COLUMN_1].setIcon(thirdColumn1);

                // Go to the next card in the deck
                if (placeInDeck <= 52) {
                    placeInDeck++;
                } else {

                    //if the user used all the 52 cards in the deck,
                    //display a message to the user and end the game
                    JOptionPane.showMessageDialog(this,
                            "You ran out of cards! " +
                                    "\n\nYour score is" +
                                    stage2.getScore() +
                                    "\nYour punishment is to do "
                                    + stage2.getScore() + " Push-ups");

                    System.exit(0);
                }

                // Make buttons disappear
                for (JButton upButtons : upPath) {
                    upButtons.setVisible(false);
                    upButtons.setEnabled(true);
                }
                for (JButton downButtons : downPath) {
                    downButtons.setVisible(false);
                    downButtons.setEnabled(true);
                }
                return;
            }
        }

        //if the user is at the bottom card of column 1 and clicks high
        else {

            //change card visibility, and set the paths
            blankCards[THIRD_COLUMN_2].setVisible(false);
            secondCol2 = false;
            thirdCol2 = true;

            // Check for face card in newly placed card
            if (stage2.checkPathFaceCard(thirdColumn2.toString())) {

                //display a message to the user
                JOptionPane.showMessageDialog(this,
                        "Yikes, face card! Restart!");

                // Cover the face card up
                for (JLabel blankCard : blankCards) {
                    blankCard.setVisible(true);
                }
                flip.setEnabled(true);

                // Change the value of the face card
                thirdColumn2 = new ImageIcon(getClass().getResource(
                        stage2.smallCardString(placeInDeck)));
                cards[THIRD_COLUMN_2].setIcon(thirdColumn2);

                // Go to the next card in the deck
                if (placeInDeck <= 52) {
                    placeInDeck++;
                } else {
                    //if the user used all the 52 cards in the deck,
                    //display a message to the user and end the game
                    JOptionPane.showMessageDialog(this,
                            "You ran out of cards! " +
                                    "\n\nYour score is" +
                                    stage2.getScore() +
                                    "\nYour punishment is to do "
                                    + stage2.getScore() + " Push-ups");

                    System.exit(0);
                }

                // Make buttons disappear
                for (JButton upButtons : upPath) {
                    upButtons.setVisible(false);
                    upButtons.setEnabled(true);
                }
                for (JButton downButtons : downPath) {
                    downButtons.setVisible(false);
                    downButtons.setEnabled(true);
                }
                return;
            }
        }

        // If there aren't any face cards, then continue
        // Change Button Visibility
        chosenPathHigh3.setVisible(true);
        chosenPathLow3.setVisible(true);
        chosenPathHigh2.setVisible(false);
        chosenPathLow2.setVisible(false);
    }

    /******************************************************************
     * If the user clicks on "Low" when in column 2,
     * check if the first card is a face card, if yes, tell the user,
     * then reset back to the beginning of the second stage. If not
     * a face a card, then proceed to the third column. Display the
     * buttons and wait for the user to click on one of the buttons.
     *****************************************************************/
    private void clickingLowerColumn2() {

        //if the user is at the top card in column 2 and clicks low
        if (secondCol1) {

            // Change card visibility and set the path
            blankCards[THIRD_COLUMN_2].setVisible(false);
            secondCol1 = false;
            thirdCol2 = true;

            // Checks for face card from the preplaced card
            if (stage2.checkPathFaceCard(thirdColumn2.toString())) {

                //display a message to the user
                JOptionPane.showMessageDialog(this,
                        "Yikes, face card! Restart!");

                // Cover the face card up
                for (JLabel blankCard : blankCards) {
                    blankCard.setVisible(true);
                }
                flip.setEnabled(true);

                // Change the value of the face card
                thirdColumn2 = new ImageIcon(getClass().getResource(
                        stage2.smallCardString(placeInDeck)));
                cards[THIRD_COLUMN_2].setIcon(thirdColumn2);

                // Go to the next card in the deck
                if (placeInDeck <= 52) {
                    placeInDeck++;
                } else {

                    //if the user used all the 52 cards in the deck,
                    //display a message to the user and end the game
                    JOptionPane.showMessageDialog(this,
                            "You ran out of cards! " +
                                    "\n\nYour score is" +
                                    stage2.getScore() +
                                    "\nYour punishment is to do "
                                    + stage2.getScore() + " Push-ups");

                    System.exit(0);
                }

                // Make buttons disappear
                for (JButton upButtons : upPath) {
                    upButtons.setVisible(false);
                    upButtons.setEnabled(true);
                }
                for (JButton downButtons : downPath) {
                    downButtons.setVisible(false);
                    downButtons.setEnabled(true);
                }
                return;
            }
        }

        //if the user is at the bottom card of column 1 and clicks low
        else {
            blankCards[THIRD_COLUMN_3].setVisible(false);
            secondCol2 = false;
            thirdCol3 = true;

            // Check for face card in newly placed card
            if (stage2.checkPathFaceCard(thirdColumn3.toString())) {

                //diaplay a message to the user
                JOptionPane.showMessageDialog(this,
                        "Yikes, face card! Restart!");

                // Cover the face card up
                for (JLabel blankCard : blankCards) {
                    blankCard.setVisible(true);
                }
                flip.setEnabled(true);

                // Change the value of the face card
                thirdColumn3 = new ImageIcon(getClass().getResource(
                        stage2.smallCardString(placeInDeck)));
                cards[THIRD_COLUMN_3].setIcon(thirdColumn3);

                // Go to the next card in the deck
                if (placeInDeck <= 52) {
                    placeInDeck++;
                } else {
                    //if the user used all the 52 cards in the deck,
                    //display a message to the user and end the game
                    JOptionPane.showMessageDialog(this,
                            "You ran out of cards! " +
                                    "\n\nYour score is" +
                                    stage2.getScore() +
                                    "\nYour punishment is to do " +
                                    stage2.getScore() + " Push-ups");

                    System.exit(0);
                }

                // Make buttons disappear
                for (JButton upButtons : upPath) {
                    upButtons.setVisible(false);
                    upButtons.setEnabled(true);
                }
                for (JButton downButtons : downPath) {
                    downButtons.setVisible(false);
                    downButtons.setEnabled(true);
                }
                return;
            }
        }

        // If there aren't any face cards, then continue
        // Change Button Visibility
        chosenPathHigh3.setVisible(true);
        chosenPathLow3.setVisible(true);
        chosenPathHigh2.setVisible(false);
        chosenPathLow2.setVisible(false);

    }

    /******************************************************************
     * If the user clicks on "High" when in column 3,
     * check if the first card is a face card, if yes, tell the user,
     * then reset back to the beginning of the second stage. If not
     * a face a card, then proceed to the fourth column. Display the
     * buttons and wait for the user to click on one of the buttons.
     *****************************************************************/
    private void clickingHigherColumn3() {

        //if the user is at the top card in column 3 and clicks high
        if (thirdCol1) {

            // Change card visibility and set the paths
            blankCards[FOURTH_COLUMN_1].setVisible(false);
            thirdCol1 = false;
            fourthCol1 = true;
            chosenPathHigh4.setEnabled(false);

            // Checks for face card from the preplaced card
            if (stage2.checkPathFaceCard(fourthColumn1.toString())) {

                //display a message to the user
                JOptionPane.showMessageDialog(this,
                        "Yikes, face card! Restart!");

                // Cover the face card up
                for (JLabel blankCard : blankCards) {
                    blankCard.setVisible(true);
                }
                flip.setEnabled(true);

                // Change the value of the face card
                fourthColumn1 = new ImageIcon(getClass().getResource(
                        stage2.smallCardString(placeInDeck)));
                cards[FOURTH_COLUMN_1].setIcon(fourthColumn1);

                // Go to the next card in the deck
                if (placeInDeck <= 52) {
                    placeInDeck++;
                } else {

                    //if the user used all the 52 cards in the deck,
                    //display a message to the user and end the game
                    JOptionPane.showMessageDialog(this,
                            "You ran out of cards! " +
                                    "\n\nYour score is" +
                                    stage2.getScore() +
                                    "\nYour punishment is to do " +
                                    stage2.getScore() + " Push-ups");

                    System.exit(0);
                }

                // Make buttons disappear
                for (JButton upButtons : upPath) {
                    upButtons.setVisible(false);
                    upButtons.setEnabled(true);
                }
                for (JButton downButtons : downPath) {
                    downButtons.setVisible(false);
                    downButtons.setEnabled(true);
                }
                return;
            }
        }

        //if the user is at the middle card in column3 and clicks "high"
        else if (thirdCol2) {

            //change the visibility and set the paths
            blankCards[FOURTH_COLUMN_2].setVisible(false);
            thirdCol2 = false;
            fourthCol2 = true;

            // Check for face card in newly placed card
            if (stage2.checkPathFaceCard(fourthColumn2.toString())) {

                //display a message to the user
                JOptionPane.showMessageDialog(this,
                        "Yikes, face card! Restart!");

                // Cover the face card up
                for (JLabel blankCard : blankCards) {
                    blankCard.setVisible(true);
                }
                flip.setEnabled(true);

                // Change the value of the face card
                fourthColumn2 = new ImageIcon(getClass().getResource(
                        stage2.smallCardString(placeInDeck)));
                cards[FOURTH_COLUMN_2].setIcon(fourthColumn2);

                // Go to the next card in the deck
                if (placeInDeck <= 52) {
                    placeInDeck++;
                } else {

                    //if the user used all the 52 cards in the deck,
                    //display a message to the user and end the game
                    JOptionPane.showMessageDialog(this,
                            "You ran out of cards! " +
                                    "\n\nYour score is" +
                                    stage2.getScore() +
                                    "\nYour punishment is to do " +
                                    stage2.getScore() + " Push-ups");

                    System.exit(0);
                }

                // Make buttons disappear and make them all enabled
                for (JButton upButtons : upPath) {
                    upButtons.setVisible(false);
                    upButtons.setEnabled(true);
                }
                for (JButton downButtons : downPath) {
                    downButtons.setVisible(false);
                    downButtons.setEnabled(true);
                }
                return;
            }
        }

        //if the user is at the bottom card in column3 and clicks "high"
        else {

            //change the visibility and set the paths
            blankCards[FOURTH_COLUMN_3].setVisible(false);
            thirdCol3 = false;
            fourthCol3 = true;

            // Check for face card in newly placed card
            if (stage2.checkPathFaceCard(fourthColumn3.toString())) {

                //display a message to the user
                JOptionPane.showMessageDialog(this,
                        "Yikes, face card! Restart!");

                // Cover the face card up
                for (JLabel blankCard : blankCards) {
                    blankCard.setVisible(true);
                }
                flip.setEnabled(true);

                // Change the value of the face card
                fourthColumn3 = new ImageIcon(getClass().getResource(
                        stage2.smallCardString(placeInDeck)));
                cards[FOURTH_COLUMN_3].setIcon(fourthColumn3);

                // Go to the next card in the deck
                if (placeInDeck <= 52) {
                    placeInDeck++;
                } else {

                    //if the user used all the 52 cards in the deck,
                    //display a message to the user and end the game
                    JOptionPane.showMessageDialog(this,
                            "You ran out of cards! " +
                                    "\n\nYour score is" +
                                    stage2.getScore() +
                                    "\nYour punishment is to do " +
                                    stage2.getScore() + " Push-ups");

                    System.exit(0);
                }

                // Make buttons disappear
                for (JButton upButtons : upPath) {
                    upButtons.setVisible(false);
                    upButtons.setEnabled(true);
                }
                for (JButton downButtons : downPath) {
                    downButtons.setVisible(false);
                    downButtons.setEnabled(true);
                }
                return;
            }
        }
        // If there aren't any face cards, then continue
        // Change Button Visibility
        chosenPathHigh4.setVisible(true);
        chosenPathLow4.setVisible(true);
        chosenPathHigh3.setVisible(false);
        chosenPathLow3.setVisible(false);
    }

    /******************************************************************
     * If the user clicks on "Low" when in column 3,
     * check if the first card is a face card, if yes, tell the user,
     * then reset back to the beginning of the second stage. If not
     * a face a card, then proceed to the fourth column. Display the
     * buttons and wait for the user to click on one of the buttons.
     *****************************************************************/
    private void clickingLowerColumn3() {

        //if the user is at the top card in column 3 and clicks low
        if (thirdCol1) {

            // Change card visibility and set the paths
            blankCards[FOURTH_COLUMN_2].setVisible(false);
            thirdCol1 = false;
            fourthCol2 = true;

            // Checks for face card from the preplaced card
            if (stage2.checkPathFaceCard(fourthColumn2.toString())) {

                //display a message to the user
                JOptionPane.showMessageDialog(this,
                        "Yikes, face card! Restart!");

                // Cover the face card up
                for (JLabel blankCard : blankCards) {
                    blankCard.setVisible(true);
                }
                flip.setEnabled(true);

                // Change the value of the face card
                fourthColumn2 = new ImageIcon(getClass().getResource(
                        stage2.smallCardString(placeInDeck)));
                cards[FOURTH_COLUMN_2].setIcon(fourthColumn2);

                // Go to the next card in the deck
                if (placeInDeck <= 52) {
                    placeInDeck++;
                } else {

                    //if the user used all the 52 cards in the deck,
                    //display a message to the user and end the game
                    JOptionPane.showMessageDialog(this,
                            "You ran out of cards! " +
                                    "\n\nYour score is" +
                                    stage2.getScore() +
                                    "\nYour punishment is to do " +
                                    stage2.getScore() + " Push-ups");

                    System.exit(0);
                }

                // Make buttons disappear
                for (JButton upButtons : upPath) {
                    upButtons.setVisible(false);
                    upButtons.setEnabled(true);
                }
                for (JButton downButtons : downPath) {
                    downButtons.setVisible(false);
                    downButtons.setEnabled(true);
                }
                return;
            }
        }

        //if the user was at the second card in column3 and clicks "low"
        else if (thirdCol2) {

            //change the visibility and set the paths
            blankCards[FOURTH_COLUMN_3].setVisible(false);
            thirdCol2 = false;
            fourthCol3 = true;

            // Check for face card in newly placed card
            if (stage2.checkPathFaceCard(fourthColumn3.toString())) {

                //display a message to the user
                JOptionPane.showMessageDialog(this,
                        "Yikes, face card! Restart!");

                // Cover the face card up
                for (JLabel blankCard : blankCards) {
                    blankCard.setVisible(true);
                }
                flip.setEnabled(true);

                // Change the value of the face card
                fourthColumn3 = new ImageIcon(getClass().getResource(
                        stage2.smallCardString(placeInDeck)));
                cards[FOURTH_COLUMN_3].setIcon(fourthColumn3);

                // Go to the next card in the deck
                if (placeInDeck <= 52) {
                    placeInDeck++;
                } else {

                    //if the user used all the 52 cards in the deck,
                    //display a message to the user and end the game
                    JOptionPane.showMessageDialog(this,
                            "You ran out of cards! " +
                                    "\n\nYour score is" +
                                    stage2.getScore() +
                                    "\nYour punishment is to do " +
                                    stage2.getScore() + " Push-ups");

                    System.exit(0);
                }

                // Make buttons disappear
                for (JButton upButtons : upPath) {
                    upButtons.setVisible(false);
                    upButtons.setEnabled(true);
                }
                for (JButton downButtons : downPath) {
                    downButtons.setVisible(false);
                    downButtons.setEnabled(true);
                }
                return;
            }
        }

        //if the user at the bottom card in column3 and clicks "low"
        else {

            //change the visibility and set the paths
            blankCards[FOURTH_COLUMN_4].setVisible(false);
            thirdCol3 = false;
            fourthCol4 = true;
            chosenPathLow4.setEnabled(false);

            // Check for face card in newly placed card
            if (stage2.checkPathFaceCard(fourthColumn4.toString())) {

                //display a message to the user
                JOptionPane.showMessageDialog(this,
                        "Yikes, face card! Restart!");

                // Cover the face card up
                for (JLabel blankCard : blankCards) {
                    blankCard.setVisible(true);
                }
                flip.setEnabled(true);

                // Change the value of the face card
                fourthColumn4 = new ImageIcon(getClass().getResource(
                        stage2.smallCardString(placeInDeck)));
                cards[FOURTH_COLUMN_4].setIcon(fourthColumn4);

                // Go to the next card in the deck
                if (placeInDeck <= 52) {
                    placeInDeck++;
                } else {

                    //if the user used all the 52 cards in the deck,
                    //display a message to the user and end the game
                    JOptionPane.showMessageDialog(this,
                            "You ran out of cards! " +
                                    "\n\nYour score is" +
                                    stage2.getScore() +
                                    "\nYour punishment is to do " +
                                    stage2.getScore() + " Push-ups");

                    System.exit(0);
                }

                // Make buttons disappear
                for (JButton upButtons : upPath) {
                    upButtons.setVisible(false);
                    upButtons.setEnabled(true);
                }
                for (JButton downButtons : downPath) {
                    downButtons.setVisible(false);
                    downButtons.setEnabled(true);
                }
                return;
            }
        }

        // If there aren't any face cards, then continue
        // Change Button Visibility
        chosenPathHigh4.setVisible(true);
        chosenPathLow4.setVisible(true);
        chosenPathHigh3.setVisible(false);
        chosenPathLow3.setVisible(false);
    }

    /******************************************************************
     * If the user clicks on "High" when in column 4,
     * check if the first card is a face card, if yes, tell the user,
     * then reset back to the beginning of the second stage. If not
     * a face a card, then proceed to the fifth column. Display the
     * buttons and wait for the user to click on one of the buttons.
     *****************************************************************/
    private void clickingHigherColumn4() {


        //if the user is at the top card on column3 and clicks high
        if (fourthCol1) {
            JOptionPane.showMessageDialog(null,
                    "You can't go up");
        }

        //if the user is at the second card on column3 and clicks high
        else if (fourthCol2) {

            // Change card visibility and set the paths
            blankCards[FIFTH_COLUMN_1].setVisible(false);
            fourthCol2 = false;
            fifthCol1 = true;
            chosenPathHigh5.setEnabled(false);

            // Checks for face card from the preplaced card
            if (stage2.checkPathFaceCard(fifthColumn1.toString())) {

                //display a message to the user
                JOptionPane.showMessageDialog(this,
                        "Yikes, face card! Restart!");

                // Cover the face card up
                for (JLabel blankCard : blankCards) {
                    blankCard.setVisible(true);
                }
                flip.setEnabled(true);

                // Change the value of the face card
                fifthColumn1 = new ImageIcon(getClass().getResource(
                        stage2.smallCardString(placeInDeck)));
                cards[FIFTH_COLUMN_1].setIcon(fifthColumn1);

                // Go to the next card in the deck
                if (placeInDeck <= 52) {
                    placeInDeck++;
                } else {

                    //if the user used all the 52 cards in the deck,
                    //display a message to the user and end the game
                    JOptionPane.showMessageDialog(this,
                            "You ran out of cards! " +
                                    "\n\nYour score is" +
                                    stage2.getScore() +
                                    "\nYour punishment is to do " +
                                    stage2.getScore() + " Push-ups");

                    System.exit(0);
                }

                // Make buttons disappear
                for (JButton upButtons : upPath) {
                    upButtons.setVisible(false);
                    upButtons.setEnabled(true);
                }
                for (JButton downButtons : downPath) {
                    downButtons.setVisible(false);
                    downButtons.setEnabled(true);
                }
                return;
            }
        }

        //if the user is at the third card on column3 and clicks high
        else if (fourthCol3) {

            //change the visibility and set the paths
            blankCards[FIFTH_COLUMN_2].setVisible(false);
            fourthCol3 = false;
            fifthCol2 = true;

            // Check for face card in newly placed card
            if (stage2.checkPathFaceCard(fifthColumn2.toString())) {


                //display a message to the user
                JOptionPane.showMessageDialog(this,
                        "Yikes, face card! Restart!");

                // Cover the face card up
                for (JLabel blankCard : blankCards) {
                    blankCard.setVisible(true);
                }
                flip.setEnabled(true);

                // Change the value of the face card
                fifthColumn2 = new ImageIcon(getClass().getResource(
                        stage2.smallCardString(placeInDeck)));
                cards[FIFTH_COLUMN_2].setIcon(fifthColumn2);

                // Go to the next card in the deck
                if (placeInDeck <= 52) {
                    placeInDeck++;
                } else {

                    //if the user used all the 52 cards in the deck,
                    //display a message to the user and end the game
                    JOptionPane.showMessageDialog(this,
                            "You ran out of cards! " +
                                    "\n\nYour score is" +
                                    stage2.getScore() +
                                    "\nYour punishment is to do " +
                                    stage2.getScore() + " Push-ups");

                    System.exit(0);
                }

                // Make buttons disappear
                for (JButton upButtons : upPath) {
                    upButtons.setVisible(false);
                    upButtons.setEnabled(true);
                }
                for (JButton downButtons : downPath) {
                    downButtons.setVisible(false);
                    downButtons.setEnabled(true);
                }
                return;
            }
        }

        //if the user is at 4th card on column3 and click high
        else {

            //change the visibility and set the paths
            blankCards[FIFTH_COLUMN_3].setVisible(false);
            fourthCol4 = false;
            fifthCol3 = true;
            chosenPathLow5.setEnabled(false);

            // Check for face card in newly placed card
            if (stage2.checkPathFaceCard(fifthColumn3.toString())) {

                //display a message to the user
                JOptionPane.showMessageDialog(this,
                        "Yikes, face card! Restart!");

                // Cover the face card up
                for (JLabel blankCard : blankCards) {
                    blankCard.setVisible(true);
                }
                flip.setEnabled(true);

                // Change the value of the face card
                fifthColumn3 = new ImageIcon(getClass().getResource(
                        stage2.smallCardString(placeInDeck)));
                cards[FIFTH_COLUMN_3].setIcon(fifthColumn3);

                // Go to the next card in the deck
                if (placeInDeck <= 52) {
                    placeInDeck++;
                } else {

                    //if the user used all the 52 cards in the deck,
                    //display a message to the user and end the game
                    JOptionPane.showMessageDialog(this,
                            "You ran out of cards! " +
                                    "\n\nYour score is" +
                                    stage2.getScore() +
                                    "\nYour punishment is to do " +
                                    stage2.getScore() + " Push-ups");

                    System.exit(0);
                }

                // Make buttons disappear
                for (JButton upButtons : upPath) {
                    upButtons.setVisible(false);
                    upButtons.setEnabled(true);
                }
                for (JButton downButtons : downPath) {
                    downButtons.setVisible(false);
                    downButtons.setEnabled(true);
                }
                return;
            }
        }

        // If there aren't any face cards, then continue
        // Change Button Visibility
        chosenPathHigh5.setVisible(true);
        chosenPathLow5.setVisible(true);
        chosenPathHigh4.setVisible(false);
        chosenPathLow4.setVisible(false);
    }

    /******************************************************************
     * If the user clicks on "Low" when in column 4,
     * check if the first card is a face card, if yes, tell the user,
     * then reset back to the beginning of the second stage. If not
     * a face a card, then proceed to the fifth column. Display the
     * buttons and wait for the user to click on one of the buttons.
     *****************************************************************/
    private void clickingLowerColumn4() {


        //if the user is at the top card of column3 and click low
        if (fourthCol1) {

            // Change card visibility and set the paths
            blankCards[FIFTH_COLUMN_1].setVisible(false);
            fourthCol1 = false;
            fifthCol1 = true;
            chosenPathHigh5.setEnabled(false);

            // Checks for face card from the preplaced card
            if (stage2.checkPathFaceCard(fifthColumn1.toString())) {

                //display a message to the user
                JOptionPane.showMessageDialog(this,
                        "Yikes, face card! Restart!");

                // Cover the face card up
                for (JLabel blankCard : blankCards) {
                    blankCard.setVisible(true);
                }
                flip.setEnabled(true);

                // Change the value of the face card
                fifthColumn1 = new ImageIcon(getClass().getResource(
                        stage2.smallCardString(placeInDeck)));
                cards[FIFTH_COLUMN_1].setIcon(fifthColumn1);
                secondStage.revalidate();

                // Go to the next card in the deck
                if (placeInDeck <= 52) {
                    placeInDeck++;
                } else {

                    //if the user used all the 52 cards in the deck,
                    //display a message to the user and end the game
                    JOptionPane.showMessageDialog(this,
                            "You ran out of cards! " +
                                    "\n\nYour score is" +
                                    stage2.getScore() +
                                    "\nYour punishment is to do " +
                                    stage2.getScore() + " Push-ups");

                    System.exit(0);
                }

                // Make buttons disappear
                for (JButton upButtons : upPath) {
                    upButtons.setVisible(false);
                    upButtons.setEnabled(true);
                }
                for (JButton downButtons : downPath) {
                    downButtons.setVisible(false);
                    downButtons.setEnabled(true);
                }
                return;
            }
        }

        //if the user is at the 2nd card on column3 and clicks low
        else if (fourthCol2) {

            //change the visibility and set the paths
            blankCards[FIFTH_COLUMN_2].setVisible(false);
            fourthCol2 = false;
            fifthCol2 = true;

            // Check for face card in newly placed card
            if (stage2.checkPathFaceCard(fifthColumn2.toString())) {

                //display a message to the user
                JOptionPane.showMessageDialog(this,
                        "Yikes, face card! Restart!");

                // Cover the face card up
                for (JLabel blankCard : blankCards) {
                    blankCard.setVisible(true);
                }
                flip.setEnabled(true);

                // Change the value of the face card
                fifthColumn2 = new ImageIcon(getClass().getResource(
                        stage2.smallCardString(placeInDeck)));
                cards[FIFTH_COLUMN_2].setIcon(fifthColumn2);

                // Go to the next card in the deck
                if (placeInDeck <= 52) {
                    placeInDeck++;
                } else {

                    //if the user used all the 52 cards in the deck,
                    //display a message to the user and end the game
                    JOptionPane.showMessageDialog(this,
                            "You ran out of cards! " +
                                    "\n\nYour score is" +
                                    stage2.getScore() +
                                    "\nYour punishment is to do " +
                                    stage2.getScore() + " Push-ups");

                    System.exit(0);
                }

                // Make buttons disappear
                for (JButton upButtons : upPath) {
                    upButtons.setVisible(false);
                    upButtons.setEnabled(true);
                }
                for (JButton downButtons : downPath) {
                    downButtons.setVisible(false);
                    downButtons.setEnabled(true);
                }
                return;
            }
        }

        //if the user is at the 3rd card on column3 and clicks low
        else if (fourthCol3) {

            //change the visibility and set the paths
            blankCards[FIFTH_COLUMN_3].setVisible(false);
            fourthCol3 = false;
            fifthCol3 = true;
            chosenPathLow5.setEnabled(false);

            // Check for face card in newly placed card
            if (stage2.checkPathFaceCard(fifthColumn3.toString())) {

                //display a message to the user
                JOptionPane.showMessageDialog(this,
                        "Yikes, face card! Restart!");

                // Cover the face card up
                for (JLabel blankCard : blankCards) {
                    blankCard.setVisible(true);
                }
                flip.setEnabled(true);

                // Change the value of the face card
                fifthColumn3 = new ImageIcon(getClass().getResource(
                        stage2.smallCardString(placeInDeck)));
                cards[FIFTH_COLUMN_3].setIcon(fifthColumn3);

                // Go to the next card in the deck
                if (placeInDeck <= 52) {
                    placeInDeck++;
                } else {

                    //if the user used all the 52 cards in the deck,
                    //display a message to the user and end the game
                    JOptionPane.showMessageDialog(this,
                            "You ran out of cards! " +
                                    "\n\nYour score is" +
                                    stage2.getScore() +
                                    "\nYour punishment is to do " +
                                    stage2.getScore() + " Push-ups");

                    System.exit(0);
                }

                // Make buttons disappear
                for (JButton upButtons : upPath) {
                    upButtons.setVisible(false);
                    upButtons.setEnabled(true);
                }
                for (JButton downButtons : downPath) {
                    downButtons.setVisible(false);
                    downButtons.setEnabled(true);
                }
                return;
            }
        }

        //if the user is at the bottom card of column3 and clicks low
        else {

            //display a message to the user
            JOptionPane.showMessageDialog(null,
                    "You can't go down");
        }

        // If there aren't any face cards, then continue
        // Change Button Visibility
        chosenPathHigh5.setVisible(true);
        chosenPathLow5.setVisible(true);
        chosenPathHigh4.setVisible(false);
        chosenPathLow4.setVisible(false);
    }

    /******************************************************************
     * If the user clicks on "High" when in column 5,
     * check if the first card is a face card, if yes, tell the user,
     * then reset back to the beginning of the second stage. If not
     * a face a card, then proceed to the sixth column. Display the
     * buttons and wait for the user to click on one of the buttons.
     *****************************************************************/
    private void clickingHigherColumn5() {


        //if the user is at the top card on column4 and clicks high
        if (fifthCol1) {

            //display a message to the user
            JOptionPane.showMessageDialog(null,
                    "You can't go up");
        }

        //if the user is at the 2nd card on column4 and clicks high
        else if (fifthCol2) {

            // Change card visibility
            blankCards[SIXTH_COLUMN_1].setVisible(false);
            fifthCol2 = false;
            sixthCol1 = true;
            chosenPathHigh6.setEnabled(false);

            // Checks for face card from the preplaced card
            if (stage2.checkPathFaceCard(sixthColumn1.toString())) {

                //display a message to the user
                JOptionPane.showMessageDialog(this,
                        "Yikes, face card! Restart!");

                // Cover the face card up
                for (JLabel blankCard : blankCards) {
                    blankCard.setVisible(true);
                }
                flip.setEnabled(true);

                // Change the value of the face card
                sixthColumn1 = new ImageIcon(getClass().getResource(
                        stage2.smallCardString(placeInDeck)));
                cards[SIXTH_COLUMN_1].setIcon(sixthColumn1);

                // Go to the next card in the deck
                if (placeInDeck <= 52) {
                    placeInDeck++;
                } else {

                    //if the user used all the 52 cards in the deck,
                    //display a message to the user and end the game
                    JOptionPane.showMessageDialog(this,
                            "You ran out of cards! " +
                                    "\n\nYour score is" +
                                    stage2.getScore() +
                                    "\nYour punishment is to do " +
                                    stage2.getScore() + " Push-ups");

                    System.exit(0);
                }

                // Make buttons disappear
                for (JButton upButtons : upPath) {
                    upButtons.setVisible(false);
                    upButtons.setEnabled(true);
                }
                for (JButton downButtons : downPath) {
                    downButtons.setVisible(false);
                    downButtons.setEnabled(true);
                }
                return;
            }
        }

        //if the user is at the bottom card on column4 and clicks high
        else {

            //change the visibility and set the paths
            blankCards[SIXTH_COLUMN_2].setVisible(false);
            fifthCol3 = false;
            sixthCol2 = true;
            chosenPathLow6.setEnabled(false);

            // Check for face card in newly placed card
            if (stage2.checkPathFaceCard(sixthColumn2.toString())) {

                //display a message to the user
                JOptionPane.showMessageDialog(this,
                        "Yikes, face card! Restart!");

                // Cover the face card up
                for (JLabel blankCard : blankCards) {
                    blankCard.setVisible(true);
                }
                flip.setEnabled(true);

                // Change the value of the face card
                sixthColumn2 = new ImageIcon(getClass().getResource(
                        stage2.smallCardString(placeInDeck)));
                cards[SIXTH_COLUMN_2].setIcon(sixthColumn2);

                // Go to the next card in the deck
                if (placeInDeck <= 52) {
                    placeInDeck++;
                } else {

                    //if the user used all the 52 cards in the deck,
                    //display a message to the user and end the game
                    JOptionPane.showMessageDialog(this,
                            "You ran out of cards! " +
                                    "\n\nYour score is" +
                                    stage2.getScore() +
                                    "\nYour punishment is to do " +
                                    stage2.getScore() + " Push-ups");

                    System.exit(0);
                }

                // Make buttons disappear
                for (JButton upButtons : upPath) {
                    upButtons.setVisible(false);
                    upButtons.setEnabled(true);
                }
                for (JButton downButtons : downPath) {
                    downButtons.setVisible(false);
                    downButtons.setEnabled(true);
                }
                return;
            }
        }

        // If there aren't any face cards, then continue
        // Change Button Visibility
        chosenPathHigh6.setVisible(true);
        chosenPathLow6.setVisible(true);
        chosenPathHigh5.setVisible(false);
        chosenPathLow5.setVisible(false);
    }

    /******************************************************************
     * If the user clicks on "Low" when in column 5,
     * check if the first card is a face card, if yes, tell the user,
     * then reset back to the beginning of the second stage. If not
     * a face a card, then proceed to the sixth column. Display the
     * buttons and wait for the user to click on one of the buttons.
     *****************************************************************/
    private void clickingLowerColumn5() {


        //if the user is at the top card of column4 and clicks low
        if (fifthCol1) {

            // Change card visibility and set the paths
            blankCards[SIXTH_COLUMN_1].setVisible(false);
            fifthCol1 = false;
            sixthCol1 = true;
            chosenPathHigh6.setEnabled(false);

            // Checks for face card from the preplaced card
            if (stage2.checkPathFaceCard(sixthColumn1.toString())) {

                //display a message to the user
                JOptionPane.showMessageDialog(this,
                        "Yikes, face card! Restart!");

                // Cover the face card up
                for (JLabel blankCard : blankCards) {
                    blankCard.setVisible(true);
                }
                flip.setEnabled(true);

                // Change the value of the face card
                sixthColumn1 = new ImageIcon(getClass().getResource(
                        stage2.smallCardString(placeInDeck)));
                cards[SIXTH_COLUMN_1].setIcon(sixthColumn1);

                // Go to the next card in the deck
                if (placeInDeck <= 52) {
                    placeInDeck++;
                } else {

                    //if the user used all the 52 cards in the deck,
                    //display a message to the user and end the game
                    JOptionPane.showMessageDialog(this,
                            "You ran out of cards! " +
                                    "\n\nYour score is" +
                                    stage2.getScore() +
                                    "\nYour punishment is to do " +
                                    stage2.getScore() + " Push-ups");

                    System.exit(0);
                }

                // Make buttons disappear
                for (JButton upButtons : upPath) {
                    upButtons.setVisible(false);
                    upButtons.setEnabled(true);
                }
                for (JButton downButtons : downPath) {
                    downButtons.setVisible(false);
                    downButtons.setEnabled(true);
                }
                return;
            }

        }

        //if the user is at the 2nd card on column4 and clicks low
        else if (fifthCol2) {

            //change the visibility and set the paths
            blankCards[SIXTH_COLUMN_2].setVisible(false);
            fifthCol2 = false;
            sixthCol2 = true;
            chosenPathLow6.setEnabled(false);

            // Check for face card in newly placed card
            if (stage2.checkPathFaceCard(sixthColumn2.toString())) {

                //display a message to the user
                JOptionPane.showMessageDialog(this,
                        "Yikes, face card! Restart!");

                // Cover the face card up
                for (JLabel blankCard : blankCards) {
                    blankCard.setVisible(true);
                }
                flip.setEnabled(true);

                // Change the value of the face card
                sixthColumn2 = new ImageIcon(getClass().getResource(
                        stage2.smallCardString(placeInDeck)));
                cards[SIXTH_COLUMN_2].setIcon(sixthColumn2);

                // Go to the next card in the deck
                if (placeInDeck <= 52) {
                    placeInDeck++;
                } else {

                    //if the user used all the 52 cards in the deck,
                    //display a message to the user and end the game
                    JOptionPane.showMessageDialog(this,
                            "You ran out of cards! " +
                                    "\n\nYour score is" +
                                    stage2.getScore() +
                                    "\nYour punishment is to do " +
                                    stage2.getScore() + " Push-ups");

                    System.exit(0);
                }

                // Make buttons disappear
                for (JButton upButtons : upPath) {
                    upButtons.setVisible(false);
                    upButtons.setEnabled(true);
                }
                for (JButton downButtons : downPath) {
                    downButtons.setVisible(false);
                    downButtons.setEnabled(true);
                }
                return;
            }
        }

        //if the user is at the bottom card of column4 and clicks low
        else {

            //display a message to the user
            JOptionPane.showMessageDialog(null,
                    "You can't go down");
        }

        // If there aren't any face cards, then continue
        // Change Button Visibility
        chosenPathHigh6.setVisible(true);
        chosenPathLow6.setVisible(true);
        chosenPathHigh5.setVisible(false);
        chosenPathLow5.setVisible(false);
    }

    /******************************************************************
     * If the user clicks on "High" when in column 6,
     * check if the first card is a face card, if yes, tell the user,
     * then reset back to the beginning of the second stage. If not
     * a face a card, then proceed to the seventh column. Display the
     * buttons and wait for the user to click on one of the buttons.
     *****************************************************************/
    private void clickingHigherColumn6() {


        //if the user is  at the top card of column5 and clicks high
        if (sixthCol1) {

            //display a message to the user
            JOptionPane.showMessageDialog(null,
                    "You can't go up");
        }

        //if the user is at the bottom card of column5 and clicks high
        else {

            // Change card visibility and set the paths
            blankCards[SEVENTH_COLUMN].setVisible(false);
            sixthCol2 = false;
            seventhCol = true;

            // Checks for face card from pre-placed card
            if (stage2.checkPathFaceCard(seventhColumn.toString())) {

                //display a message to the user
                JOptionPane.showMessageDialog(this,
                        "Yikes, face card! Restart!");

                // Cover the face card up
                for (JLabel blankCard : blankCards) {
                    blankCard.setVisible(true);
                }
                flip.setEnabled(true);

                // Change the value of the face card
                seventhColumn = new ImageIcon(getClass().getResource(
                        stage2.smallCardString(placeInDeck)));
                cards[SEVENTH_COLUMN].setIcon(seventhColumn);

                // Go to the next card in the deck
                if (placeInDeck <= 52) {
                    placeInDeck++;
                } else {

                    //if the user used all the 52 cards in the deck,
                    //display a message to the user and end the game
                    JOptionPane.showMessageDialog(this,
                            "You ran out of cards! " +
                                    "\n\nYour score is " +
                                    stage2.getScore() +
                                    "\nYour punishment is to do " +
                                    stage2.getScore() + " Push-ups");

                    System.exit(0);
                }

                // Make buttons disappear
                for (JButton upButtons : upPath) {
                    upButtons.setVisible(false);
                    upButtons.setEnabled(true);
                }
                for (JButton downButtons : downPath) {
                    downButtons.setVisible(false);
                    downButtons.setEnabled(true);
                }
                return;
            }
        }

        // If there aren't any face cards, then continue
        // Change Button Visibility
        chosenPathHigh6.setVisible(false);
        chosenPathLow6.setVisible(false);

        //try to sleep for a second
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {

            //if interrupted, print the trace
            e.printStackTrace();
        }

        //display ending message to the user
        JOptionPane.showMessageDialog(null,
                ("Thank you for playing Ride the Bus!!! " +
                        "\n\nYour score is "
                        + stage2.getScore() +
                        "\nYour punishment is to do " +
                        stage2.getScore() + " Push-ups"),
                "Done", JOptionPane.PLAIN_MESSAGE);


        //exit the program
        System.exit(0);

    }

    /******************************************************************
     * If the user clicks on "Low" when in column 6,
     * check if the first card is a face card, if yes, tell the user,
     * then reset back to the beginning of the second stage. If not
     * a face a card, then proceed to the seventh column. Display the
     * buttons and wait for the user to click on one of the buttons.
     *****************************************************************/
    private void clickingLowerColumn6() {


        //if the user is at the top card on column5 and clicks low
        if (sixthCol1) {

            // Change card visibility and set the paths
            blankCards[SEVENTH_COLUMN].setVisible(false);
            sixthCol1 = false;
            seventhCol = true;

            // Checks for face card from pre-placed card
            if (stage2.checkPathFaceCard(seventhColumn.toString())) {

                //display a message to the user
                JOptionPane.showMessageDialog(this,
                        "Yikes, face card! Restart!");

                // Cover the face card up
                for (JLabel blankCard : blankCards) {
                    blankCard.setVisible(true);
                }
                flip.setEnabled(true);

                // Change the value of the face card
                seventhColumn = new ImageIcon(getClass().getResource(
                        stage2.smallCardString(placeInDeck)));
                cards[SEVENTH_COLUMN].setIcon(seventhColumn);

                // Go to the next card in the deck
                if (placeInDeck <= 52) {
                    placeInDeck++;
                } else {

                    //if the user used all the 52 cards in the deck,
                    //display a message to the user and end the game
                    JOptionPane.showMessageDialog(this,
                            "You ran out of cards! " +
                                    "\n\nYour score is " +
                                    stage2.getScore() +
                                    "\nYour punishment is to do " +
                                    stage2.getScore() + " Push-ups");

                    System.exit(0);
                }

                // Make buttons disappear
                for (JButton upButtons : upPath) {
                    upButtons.setVisible(false);
                    upButtons.setEnabled(true);
                }
                for (JButton downButtons : downPath) {
                    downButtons.setVisible(false);
                    downButtons.setEnabled(true);
                }
                return;
            }
        }

        //if the user is at the bottom card of column5 and clicks low
        else {

            //display a message to the user
            JOptionPane.showMessageDialog(null,
                    "You can't go down");
        }

        // If there aren't any face cards, then continue
        // Change Button Visibility
        chosenPathHigh6.setVisible(false);
        chosenPathLow6.setVisible(false);

        //try to sleep for a second
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {

            //if interrupted, print the trace
            e.printStackTrace();
        }

        //display the ending message to the user
        JOptionPane.showMessageDialog(null,
                ("Thank you for playing Ride the Bus!!! " +
                        "\n\nYour score is "
                        + stage2.getScore() +
                        "\nYour punishment is to do " +
                        stage2.getScore() + " Push-ups"),
                "Done", JOptionPane.PLAIN_MESSAGE);

        //exit the program
        System.exit(0);

    }

    /******************************************************************
     * This is a helper method to determine if the user guessed the
     * correct color of the card (first stage, question one)
     *
     * @param choice which button the user clicked, red or black
     *****************************************************************/
    private void clickingColor(Object choice) {

        // Sets the choice the user makes to a string
        String cardColor;
        if (choice == blackButton) {
            cardColor = "black";
        } else {
            cardColor = "red";
        }

        // Make color buttons invisible
        for (JButton color : colors) {
            color.setVisible(false);
        }

        //make the card visible
        redBlackLabel.setVisible(true);

        System.out.println(redBlackCard.toString());

        // Checks if card is red or black and
        // display a message accordingly
        if (stage1.redVsBlackScore(redBlackCard.toString(),
                cardColor)) {
            JOptionPane.showMessageDialog(null,
                    "You got it right!");
        } else {
            JOptionPane.showMessageDialog(null,
                    "Ouch!");
        }

        // Make highLow buttons visible
        for (JButton aHighLow : highLow) {
            aHighLow.setVisible(true);
        }

        //change the question
        questionLabel.setText("Higher or lower than " +
                "the previous card?");

    }

    /******************************************************************
     * This is a helper method to determine if the user guessed
     * correctly, if the second card is higher or lower than card one.
     * (First stage, question two)
     *
     * @param choice which button the user clicked. They can pick
     *              high, low or equal
     *****************************************************************/
    private void clickingHighOrLow(Object choice) {

        //make the card visible
        highLowLabel.setVisible(true);

        //variable to convert the user's choice to a string
        String userChoice;

        //read the user's choice and assign the correct value
        // to compare it
        if (choice == higherButton) {
            userChoice = "higher";
        } else if (choice == lowerButton) {
            userChoice = "lower";
        } else {
            userChoice = "equal";
        }


        // Make highLow buttons invisible
        for (JButton aHighLow : highLow) {
            aHighLow.setVisible(false);
        }

        //decide if the user guessed correctly or not and
        //display a message accordingly
        if (stage1.lowerOrHigherScore(userChoice)) {
            JOptionPane.showMessageDialog(null,
                    "Correct");
        } else {
            JOptionPane.showMessageDialog(null,
                    "Ouch");
        }


        // Makes inOut buttons visible
        for (JButton anInOut : inOut) {
            anInOut.setVisible(true);
        }

        //change the question
        questionLabel.setText("Inside or outside " +
                "the previous two cards?");
    }

    /******************************************************************
     * This is a helper method to determine if the user guessed
     * correctly, if the third card's value is between the first two
     * or outside. (First stage, question three)
     *
     * @param choice which the button the user clicked, inside,
     *               outside or equal.
     *****************************************************************/
    private void clickingInOrOut(Object choice) {

        //make the card visible
        inOutLabel.setVisible(true);

        //variable to convert the user's choice to a string
        String userChoice;

        //read the user's choice and assign the string accordingly
        if (choice == insideButton)
            userChoice = "inside";
        else if (choice == outsideButton)
            userChoice = "outside";
        else
            userChoice = "equal";

        // Makes inOut buttons invisible
        for (JButton anInOut : inOut) {
            anInOut.setVisible(false);
        }

        //decide if the user guessed correctly or not, and display
        //a message accordingly.
        if (stage1.insideOrOutsideScore(userChoice)) {

            JOptionPane.showMessageDialog(null,
                    "Correct");
        } else {
            JOptionPane.showMessageDialog(null,
                    "Ouch");
        }

        // Make suit buttons visible
        for (JButton suit : suits) {
            suit.setVisible(true);
        }

        //change the question
        questionLabel.setText("What's the suit of the card?");
    }

    /******************************************************************
     * This is a helper method to determine if the user guessed
     * correctly, what is the suit of the dealer's card.
     * (First stage, question four)
     *
     * @param choice which button the user clicked, clubs, diamonds,
     *               spades, hearts.
     *****************************************************************/
    private void clickingSuit(Object choice) {

        //make the card visible
        suitLabel.setVisible(true);

        // Make suit buttons invisible
        for (JButton suit : suits) {
            suit.setVisible(false);
        }

        // Sets the choice the user makes
        String cardSuit;
        if (choice == spadesButton) {
            cardSuit = "spades";
        } else if (choice == heartsButton) {
            cardSuit = "hearts";
        } else if (choice == clubsButton) {
            cardSuit = "clubs";
        } else {
            cardSuit = "diamonds";
        }

        // Checks if card is correct suit
        if (stage1.guessSuitScore(suitCard.toString(), cardSuit)) {
            JOptionPane.showMessageDialog(null,
                    "You got it right!");

        } else {
            JOptionPane.showMessageDialog(null,
                    "Ouch!");
        }

        // Make the number buttons visible
        for (JButton number : numbers) {
            number.setVisible(true);
        }

        //change the question
        questionLabel.setText("What's the value of the card?");
    }

    /******************************************************************
     * This is a helper method to determine if the user guessed
     * correctly, what is the value of the dealer's card.
     * (First stage, question five)
     *
     * @param choice which button the user clicked, A, 2, 3, 4, 5, 6
     *               7, 8, 9, 10, J, Q, K
     *****************************************************************/
    private void clickingNumber(Object choice) {

        //make the card visible
        randomLabel.setVisible(true);

        // Make the number buttons invisible
        for (JButton number : numbers) {
            number.setVisible(false);
        }

        //get the user's choice and store it in a string
        String userChoice;

        if (choice == twoButton) userChoice = "2";
        else if (choice == threeButton) userChoice = "3";
        else if (choice == fourButton) userChoice = "4";
        else if (choice == fiveButton) userChoice = "5";
        else if (choice == sixButton) userChoice = "6";
        else if (choice == sevenButton) userChoice = "7";
        else if (choice == eightButton) userChoice = "8";
        else if (choice == nineButton) userChoice = "9";
        else if (choice == tenButton) userChoice = "10";
        else if (choice == jackButton) userChoice = "jack";
        else if (choice == queenButton) userChoice = "queen";
        else if (choice == kingButton) userChoice = "king";
        else if (choice == aceButton) userChoice = "ace";
        else userChoice = "Something happened";

        System.out.println("User choice: " + userChoice);
        System.out.println(randomCard.toString());

        // Update the points
        pointsLabel.setText("Players points: " + stage1.getScore());

        //decide if the user guessed correctly or not, display
        //a message accordingly
        if (stage1.guessCardValueScore(randomCard.toString(),
                userChoice)) {
            JOptionPane.showMessageDialog(null,
                    "Awesome!!!");
        } else {
            JOptionPane.showMessageDialog(null,
                    "Damn it!");
        }

        //change the question
        questionLabel.setText("The first stage is done. " +
                "Wait a second");

        //sleep for 2 seconds
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e1) {
            System.out.println("The time got interrupted");
        }

        //display a message to tell the user it is time for stage two
        JOptionPane.showMessageDialog(null,
                "Time for stage 2!");

        // Sets first stage panels to false so stage two can begin
        firstStage.setVisible(false);
        secondStage.setVisible(true);

        // Take the points from stage one and put them
        // to the stage two game
        stage2.setScore(stage1.getScore());

        //change the questions label for stage two
        questionLabel.setText("<html>Find a path to cross " +
                "the diamond without hitting a face card<br>" +
                "**if you flip a face card you will restart**");
        questionLabel.setFont(new Font("Cooper Black",
                Font.BOLD, 30));
    }
}