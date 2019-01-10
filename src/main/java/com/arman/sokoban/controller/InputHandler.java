package com.arman.sokoban.controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class InputHandler implements MouseListener, MouseMotionListener, MouseWheelListener {

    public static final String UP_PRESSED = "UP";
    public static final String DOWN_PRESSED = "DOWN";
    public static final String LEFT_PRESSED = "LEFT";
    public static final String RIGHT_PRESSED = "RIGHT";
    public static final String ENTER_PRESSED = "ENTER";
    public static final String ESCAPE_PRESSED = "ESCAPE";
    public static final String Q_PRESSED = "Q";
    public static final String W_PRESSED = "W";
    public static final String E_PRESSED = "E";
    public static final String R_PRESSED = "R";
    public static final String T_PRESSED = "T";
    public static final String Y_PRESSED = "Y";
    public static final String U_PRESSED = "U";
    public static final String I_PRESSED = "I";
    public static final String O_PRESSED = "O";
    public static final String P_PRESSED = "P";
    public static final String A_PRESSED = "A";
    public static final String S_PRESSED = "S";
    public static final String D_PRESSED = "D";
    public static final String F_PRESSED = "F";
    public static final String G_PRESSED = "G";
    public static final String H_PRESSED = "H";
    public static final String J_PRESSED = "J";
    public static final String K_PRESSED = "K";
    public static final String L_PRESSED = "L";
    public static final String Z_PRESSED = "Z";
    public static final String X_PRESSED = "X";
    public static final String C_PRESSED = "C";
    public static final String V_PRESSED = "V";
    public static final String B_PRESSED = "B";
    public static final String N_PRESSED = "N";
    public static final String M_PRESSED = "M";
    public static final String SHIFT_PRESSED = "SHIFT";
    public static final String CTRL_PRESSED = "CONTROL";
    public static final String ONE_PRESSED = "1";
    public static final String TWO_PRESSED = "2";
    public static final String THREE_PRESSED = "3";
    public static final String FOUR_PRESSED = "4";
    public static final String FIVE_PRESSED = "5";
    public static final String SIX_PRESSED = "6";
    public static final String SEVEN_PRESSED = "7";
    public static final String EIGHT_PRESSED = "8";
    public static final String NINE_PRESSED = "9";

    public static final String UP_RELEASED = "released UP";
    public static final String DOWN_RELEASED = "released DOWN";
    public static final String LEFT_RELEASED = "released LEFT";
    public static final String RIGHT_RELEASED = "released RIGHT";
    public static final String ENTER_RELEASED = "released ENTER";
    public static final String ESCAPE_RELEASED = "released ESCAPE";
    public static final String Q_RELEASED = "released Q";
    public static final String W_RELEASED = "released W";
    public static final String E_RELEASED = "released E";
    public static final String R_RELEASED = "released R";
    public static final String T_RELEASED = "released T";
    public static final String Y_RELEASED = "released Y";
    public static final String U_RELEASED = "released U";
    public static final String I_RELEASED = "released I";
    public static final String O_RELEASED = "released O";
    public static final String P_RELEASED = "released P";
    public static final String A_RELEASED = "released A";
    public static final String S_RELEASED = "released S";
    public static final String D_RELEASED = "released D";
    public static final String F_RELEASED = "released F";
    public static final String G_RELEASED = "released G";
    public static final String H_RELEASED = "released H";
    public static final String J_RELEASED = "released J";
    public static final String K_RELEASED = "released K";
    public static final String L_RELEASED = "released L";
    public static final String Z_RELEASED = "released Z";
    public static final String X_RELEASED = "released X";
    public static final String C_RELEASED = "released C";
    public static final String V_RELEASED = "released V";
    public static final String B_RELEASED = "released B";
    public static final String N_RELEASED = "released N";
    public static final String M_RELEASED = "released M";
    public static final String SHIFT_RELEASED = "released SHIFT";
    public static final String CTRL_RELEASED = "released CONTROL";
    public static final String ONE_RELEASED = "released 1";
    public static final String TWO_RELEASED = "released 2";
    public static final String THREE_RELEASED = "released 3";
    public static final String FOUR_RElEASED = "released 4";
    public static final String FIVE_RELEASED = "released 5";
    public static final String SIX_RELEASED = "released 6";
    public static final String SEVEN_RELEASED = "released 7";
    public static final String EIGHT_RELEASED = "released 8";
    public static final String NINE_RELEASED = "released 9";

    public static final int UP = 0;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    public static final int MOUSE_LEFT = 4;
    public static final int MOUSE_RIGHT = 5;
    public static final int ENTER = 6;
    public static final int ESCAPE = 7;
    public static final int Q = 8;
    public static final int W = 9;
    public static final int E = 10;
    public static final int R = 11;
    public static final int T = 12;
    public static final int Y = 13;
    public static final int U = 14;
    public static final int I = 15;
    public static final int O = 16;
    public static final int P = 17;
    public static final int A = 18;
    public static final int S = 19;
    public static final int D = 20;
    public static final int F = 21;
    public static final int G = 22;
    public static final int H = 23;
    public static final int J = 24;
    public static final int K = 25;
    public static final int L = 26;
    public static final int Z = 27;
    public static final int X = 28;
    public static final int C = 29;
    public static final int V = 30;
    public static final int B = 31;
    public static final int N = 32;
    public static final int M = 33;
    public static final int SHIFT = 34;
    public static final int CTRL = 35;
    public static final int ONE = 36;
    public static final int TWO = 37;
    public static final int THREE = 38;
    public static final int FOUR = 39;
    public static final int FIVE = 40;
    public static final int SIX = 41;
    public static final int SEVEN = 42;
    public static final int EIGHT = 43;
    public static final int NINE = 44;
    public static final int SCROLL_WHEEL = 45;

    private static final int NUM_KEYS = 46;

    private static InputHandler instance;

    private static boolean[] keysDown;
    private static boolean[] prevKeysDown;

    private static boolean mouseInsideWindow;

    private static int mouseX;
    private static int mouseY;
    private static int scroll;

    static {
        instance = new InputHandler();
        keysDown = new boolean[NUM_KEYS];
        prevKeysDown = new boolean[NUM_KEYS];
        mouseX = 0;
        mouseY = 0;
        scroll = 0;
        mouseInsideWindow = true;
    }

    private InputHandler() {

    }

    public static InputHandler getInstance() {
        return instance;
    }

    public static void setComponent(JComponent comp) {
        InputHandler.getInstance().setComp(comp);
    }

    public static void setKey(int keycode, boolean b) {
        keysDown[keycode] = b;
    }

    public static void update() {
        scroll = 0;
        for (int i = 0; i < NUM_KEYS; i++) {
            prevKeysDown[i] = keysDown[i];
        }
    }

    private static boolean keyPressed(int keycode) {
        return keysDown[keycode] && !prevKeysDown[keycode];
    }

    private static boolean keyHold(int keycode) {
        return keysDown[keycode];
    }

    private static boolean keyReleased(int keycode) {
        return !keysDown[keycode] && prevKeysDown[keycode];
    }

    public static boolean holdUp() {
        return keyHold(UP);
    }

    public static boolean holdDown() {
        return keyHold(DOWN);
    }

    public static boolean holdLeft() {
        return keyHold(LEFT);
    }

    public static boolean holdRight() {
        return keyHold(RIGHT);
    }

    public static boolean holdQ() {
        return keyHold(Q);
    }

    public static boolean holdW() {
        return keyHold(W);
    }

    public static boolean holdE() {
        return keyHold(E);
    }

    public static boolean holdR() {
        return keyHold(R);
    }

    public static boolean holdT() {
        return keyHold(T);
    }

    public static boolean holdY() {
        return keyHold(Y);
    }

    public static boolean holdU() {
        return keyHold(U);
    }

    public static boolean holdI() {
        return keyHold(I);
    }

    public static boolean holdO() {
        return keyHold(O);
    }

    public static boolean holdP() {
        return keyHold(P);
    }

    public static boolean holdA() {
        return keyHold(A);
    }

    public static boolean holdS() {
        return keyHold(S);
    }

    public static boolean holdD() {
        return keyHold(D);
    }

    public static boolean holdF() {
        return keyHold(F);
    }

    public static boolean holdG() {
        return keyHold(G);
    }

    public static boolean holdH() {
        return keyHold(H);
    }

    public static boolean holdJ() {
        return keyHold(J);
    }

    public static boolean holdK() {
        return keyHold(K);
    }

    public static boolean holdL() {
        return keyHold(L);
    }

    public static boolean holdZ() {
        return keyHold(Z);
    }

    public static boolean holdX() {
        return keyHold(X);
    }

    public static boolean holdC() {
        return keyHold(C);
    }

    public static boolean holdV() {
        return keyHold(V);
    }

    public static boolean holdB() {
        return keyHold(B);
    }

    public static boolean holdN() {
        return keyHold(N);
    }

    public static boolean holdM() {
        return keyHold(M);
    }

    public static boolean holdShift() {
        return keyHold(SHIFT);
    }

    public static boolean holdCtrl() {
        return keyHold(CTRL);
    }

    public static boolean hold1() {
        return keyHold(ONE);
    }

    public static boolean hold2() {
        return keyHold(TWO);
    }

    public static boolean hold3() {
        return keyHold(THREE);
    }

    public static boolean hold4() {
        return keyHold(FOUR);
    }

    public static boolean hold5() {
        return keyHold(FIVE);
    }

    public static boolean hold6() {
        return keyHold(SIX);
    }

    public static boolean hold7() {
        return keyHold(SEVEN);
    }

    public static boolean hold8() {
        return keyHold(EIGHT);
    }

    public static boolean hold9() {
        return keyHold(NINE);
    }

    public static boolean holdMouseLeft() {
        return keyHold(MOUSE_LEFT);
    }

    public static boolean holdMouseRight() {
        return keyHold(MOUSE_RIGHT);
    }

    public static boolean holdScrollWheel() {
        return keyHold(SCROLL_WHEEL);
    }

    public static boolean holdEnter() {
        return keyHold(ENTER);
    }

    public static boolean holdEscape() {
        return keyHold(ESCAPE);
    }

    public static boolean releasedUp() {
        return keyReleased(UP);
    }

    public static boolean releasedDown() {
        return keyReleased(DOWN);
    }

    public static boolean releasedLeft() {
        return keyReleased(LEFT);
    }

    public static boolean releasedRight() {
        return keyReleased(RIGHT);
    }

    public static boolean releasedQ() {
        return keyReleased(Q);
    }

    public static boolean releasedW() {
        return keyReleased(W);
    }

    public static boolean releasedE() {
        return keyReleased(E);
    }

    public static boolean releasedR() {
        return keyReleased(R);
    }

    public static boolean releasedT() {
        return keyReleased(T);
    }

    public static boolean releasedY() {
        return keyReleased(Y);
    }

    public static boolean releasedU() {
        return keyReleased(U);
    }

    public static boolean releasedI() {
        return keyReleased(I);
    }

    public static boolean releasedO() {
        return keyReleased(O);
    }

    public static boolean releasedP() {
        return keyReleased(P);
    }

    public static boolean releasedA() {
        return keyReleased(A);
    }

    public static boolean releasedS() {
        return keyReleased(S);
    }

    public static boolean releasedD() {
        return keyReleased(D);
    }

    public static boolean releasedF() {
        return keyReleased(F);
    }

    public static boolean releasedG() {
        return keyReleased(G);
    }

    public static boolean releasedH() {
        return keyReleased(H);
    }

    public static boolean releasedJ() {
        return keyReleased(J);
    }

    public static boolean releasedK() {
        return keyReleased(K);
    }

    public static boolean releasedL() {
        return keyReleased(L);
    }

    public static boolean releasedZ() {
        return keyReleased(Z);
    }

    public static boolean releasedX() {
        return keyReleased(X);
    }

    public static boolean releasedC() {
        return keyReleased(C);
    }

    public static boolean releasedV() {
        return keyReleased(V);
    }

    public static boolean releasedB() {
        return  keyReleased(B);
    }

    public static boolean releasedN() {
        return keyReleased(N);
    }

    public static boolean releasedM() {
        return keyReleased(M);
    }

    public static boolean releasedShift() {
        return keyReleased(SHIFT);
    }

    public static boolean releasedCtrl() {
        return keyReleased(CTRL);
    }

    public static boolean released1() {
        return keyReleased(ONE);
    }

    public static boolean released2() {
        return keyReleased(TWO);
    }

    public static boolean released3() {
        return keyReleased(THREE);
    }

    public static boolean released4() {
        return keyReleased(FOUR);
    }

    public static boolean released5() {
        return keyReleased(FIVE);
    }

    public static boolean released6() {
        return keyReleased(SIX);
    }

    public static boolean released7() {
        return keyReleased(SEVEN);
    }

    public static boolean released8() {
        return keyReleased(EIGHT);
    }

    public static boolean released9() {
        return keyReleased(NINE);
    }

    public static boolean releasedMouseLeft() {
        return keyReleased(MOUSE_LEFT);
    }

    public static boolean releasedMouseRight() {
        return keyReleased(MOUSE_RIGHT);
    }

    public static boolean releasedScrollWheel() {
        return keyReleased(SCROLL_WHEEL);
    }

    public static boolean releasedEnter() {
        return keyReleased(ENTER);
    }

    public static boolean releasedEscape() {
        return keyReleased(ESCAPE);
    }

    public static boolean pressedUp() {
        return keyPressed(UP);
    }

    public static boolean pressedDown() {
        return keyPressed(DOWN);
    }

    public static boolean pressedLeft() {
        return keyPressed(LEFT);
    }

    public static boolean pressedRight() {
        return keyPressed(RIGHT);
    }

    public static boolean pressedQ() {
        return keyPressed(Q);
    }

    public static boolean pressedW() {
        return keyPressed(W);
    }

    public static boolean pressedE() {
        return keyPressed(E);
    }

    public static boolean pressedR() {
        return keyPressed(R);
    }

    public static boolean pressedT() {
        return keyPressed(T);
    }

    public static boolean pressedY() {
        return keyPressed(Y);
    }

    public static boolean pressedU() {
        return keyPressed(U);
    }

    public static boolean pressedI() {
        return keyPressed(I);
    }

    public static boolean pressedO() {
        return keyPressed(O);
    }

    public static boolean pressedP() {
        return keyPressed(P);
    }

    public static boolean pressedA() {
        return keyPressed(A);
    }

    public static boolean pressedS() {
        return keyPressed(S);
    }

    public static boolean pressedD() {
        return keyPressed(D);
    }

    public static boolean pressedF() {
        return keyPressed(F);
    }

    public static boolean pressedG() {
        return keyPressed(G);
    }

    public static boolean pressedH() {
        return keyPressed(H);
    }

    public static boolean pressedJ() {
        return keyPressed(J);
    }

    public static boolean pressedK() {
        return keyPressed(K);
    }

    public static boolean pressedL() {
        return keyPressed(L);
    }

    public static boolean pressedZ() {
        return keyPressed(Z);
    }

    public static boolean pressedX() {
        return keyPressed(X);
    }

    public static boolean pressedC() {
        return keyPressed(C);
    }

    public static boolean pressedV() {
        return keyPressed(V);
    }

    public static boolean pressedB() {
        return keyPressed(B);
    }

    public static boolean pressedN() {
        return keyPressed(N);
    }

    public static boolean pressedM() {
        return keyPressed(M);
    }

    public static boolean pressedShift() {
        return keyPressed(SHIFT);
    }

    public static boolean pressedCtrl() {
        return keyPressed(CTRL);
    }

    public static boolean pressed1() {
        return keyPressed(ONE);
    }

    public static boolean pressed2() {
        return keyPressed(TWO);
    }

    public static boolean pressed3() {
        return keyPressed(THREE);
    }

    public static boolean pressed4() {
        return keyPressed(FOUR);
    }

    public static boolean pressed5() {
        return keyPressed(FIVE);
    }

    public static boolean pressed6() {
        return keyPressed(SIX);
    }

    public static boolean pressed7() {
        return keyPressed(SEVEN);
    }

    public static boolean pressed8() {
        return keyPressed(EIGHT);
    }

    public static boolean pressed9() {
        return keyPressed(NINE);
    }

    public static boolean pressedMouseLeft() {
        return keyPressed(MOUSE_LEFT);
    }

    public static boolean pressedMouseRight() {
        return keyPressed(MOUSE_RIGHT);
    }

    public static boolean pressedScrollWheel() {
        return keyPressed(SCROLL_WHEEL);
    }

    public static boolean pressedEnter() {
        return keyPressed(ENTER);
    }

    public static boolean pressedEscape() {
        return keyPressed(ESCAPE);
    }

    private void setComp(JComponent component) {
        addInputs(component);
        addActions(component);
    }

    private void addInputs(JComponent component) {
        InputMap inputMap = component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);

        inputMap.put(KeyStroke.getKeyStroke(UP_PRESSED), UP_PRESSED);
        inputMap.put(KeyStroke.getKeyStroke(DOWN_PRESSED), DOWN_PRESSED);
        inputMap.put(KeyStroke.getKeyStroke(LEFT_PRESSED), LEFT_PRESSED);
        inputMap.put(KeyStroke.getKeyStroke(RIGHT_PRESSED), RIGHT_PRESSED);
        inputMap.put(KeyStroke.getKeyStroke(ESCAPE_PRESSED), ESCAPE_PRESSED);
        inputMap.put(KeyStroke.getKeyStroke(ENTER_PRESSED), ENTER_PRESSED);
        inputMap.put(KeyStroke.getKeyStroke(Q_PRESSED), Q_PRESSED);
        inputMap.put(KeyStroke.getKeyStroke(W_PRESSED), W_PRESSED);
        inputMap.put(KeyStroke.getKeyStroke(E_PRESSED), E_PRESSED);
        inputMap.put(KeyStroke.getKeyStroke(R_PRESSED), R_PRESSED);
        inputMap.put(KeyStroke.getKeyStroke(T_PRESSED), T_PRESSED);
        inputMap.put(KeyStroke.getKeyStroke(Y_PRESSED), Y_PRESSED);
        inputMap.put(KeyStroke.getKeyStroke(U_PRESSED), U_PRESSED);
        inputMap.put(KeyStroke.getKeyStroke(I_PRESSED), I_PRESSED);
        inputMap.put(KeyStroke.getKeyStroke(O_PRESSED), O_PRESSED);
        inputMap.put(KeyStroke.getKeyStroke(P_PRESSED), P_PRESSED);
        inputMap.put(KeyStroke.getKeyStroke(A_PRESSED), A_PRESSED);
        inputMap.put(KeyStroke.getKeyStroke(S_PRESSED), S_PRESSED);
        inputMap.put(KeyStroke.getKeyStroke(D_PRESSED), D_PRESSED);
        inputMap.put(KeyStroke.getKeyStroke(F_PRESSED), F_PRESSED);
        inputMap.put(KeyStroke.getKeyStroke(G_PRESSED), G_PRESSED);
        inputMap.put(KeyStroke.getKeyStroke(H_PRESSED), H_PRESSED);
        inputMap.put(KeyStroke.getKeyStroke(J_PRESSED), J_PRESSED);
        inputMap.put(KeyStroke.getKeyStroke(K_PRESSED), K_PRESSED);
        inputMap.put(KeyStroke.getKeyStroke(L_PRESSED), L_PRESSED);
        inputMap.put(KeyStroke.getKeyStroke(Z_PRESSED), Z_PRESSED);
        inputMap.put(KeyStroke.getKeyStroke(X_PRESSED), X_PRESSED);
        inputMap.put(KeyStroke.getKeyStroke(C_PRESSED), C_PRESSED);
        inputMap.put(KeyStroke.getKeyStroke(V_PRESSED), V_PRESSED);
        inputMap.put(KeyStroke.getKeyStroke(B_PRESSED), B_PRESSED);
        inputMap.put(KeyStroke.getKeyStroke(N_PRESSED), N_PRESSED);
        inputMap.put(KeyStroke.getKeyStroke(M_PRESSED), M_PRESSED);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_SHIFT, KeyEvent.SHIFT_DOWN_MASK), SHIFT_PRESSED);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_CONTROL, KeyEvent.CTRL_DOWN_MASK), CTRL_PRESSED);
        inputMap.put(KeyStroke.getKeyStroke(ONE_PRESSED), ONE_PRESSED);
        inputMap.put(KeyStroke.getKeyStroke(TWO_PRESSED), TWO_PRESSED);
        inputMap.put(KeyStroke.getKeyStroke(THREE_PRESSED), THREE_PRESSED);
        inputMap.put(KeyStroke.getKeyStroke(FOUR_PRESSED), FOUR_PRESSED);
        inputMap.put(KeyStroke.getKeyStroke(FIVE_PRESSED), FIVE_PRESSED);
        inputMap.put(KeyStroke.getKeyStroke(SIX_PRESSED), SIX_PRESSED);
        inputMap.put(KeyStroke.getKeyStroke(SEVEN_PRESSED), SEVEN_PRESSED);
        inputMap.put(KeyStroke.getKeyStroke(EIGHT_PRESSED), EIGHT_PRESSED);
        inputMap.put(KeyStroke.getKeyStroke(NINE_PRESSED), NINE_PRESSED);

        inputMap.put(KeyStroke.getKeyStroke(UP_RELEASED), UP_RELEASED);
        inputMap.put(KeyStroke.getKeyStroke(DOWN_RELEASED), DOWN_RELEASED);
        inputMap.put(KeyStroke.getKeyStroke(LEFT_RELEASED), LEFT_RELEASED);
        inputMap.put(KeyStroke.getKeyStroke(RIGHT_RELEASED), RIGHT_RELEASED);
        inputMap.put(KeyStroke.getKeyStroke(ESCAPE_RELEASED), ESCAPE_RELEASED);
        inputMap.put(KeyStroke.getKeyStroke(ENTER_RELEASED), ENTER_RELEASED);
        inputMap.put(KeyStroke.getKeyStroke(Q_RELEASED), Q_RELEASED);
        inputMap.put(KeyStroke.getKeyStroke(W_RELEASED), W_RELEASED);
        inputMap.put(KeyStroke.getKeyStroke(E_RELEASED), E_RELEASED);
        inputMap.put(KeyStroke.getKeyStroke(R_RELEASED), R_RELEASED);
        inputMap.put(KeyStroke.getKeyStroke(T_RELEASED), T_RELEASED);
        inputMap.put(KeyStroke.getKeyStroke(Y_RELEASED), Y_RELEASED);
        inputMap.put(KeyStroke.getKeyStroke(U_RELEASED), U_RELEASED);
        inputMap.put(KeyStroke.getKeyStroke(I_RELEASED), I_RELEASED);
        inputMap.put(KeyStroke.getKeyStroke(O_RELEASED), O_RELEASED);
        inputMap.put(KeyStroke.getKeyStroke(P_RELEASED), P_RELEASED);
        inputMap.put(KeyStroke.getKeyStroke(A_RELEASED), A_RELEASED);
        inputMap.put(KeyStroke.getKeyStroke(S_RELEASED), S_RELEASED);
        inputMap.put(KeyStroke.getKeyStroke(D_RELEASED), D_RELEASED);
        inputMap.put(KeyStroke.getKeyStroke(F_RELEASED), F_RELEASED);
        inputMap.put(KeyStroke.getKeyStroke(G_RELEASED), G_RELEASED);
        inputMap.put(KeyStroke.getKeyStroke(H_RELEASED), H_RELEASED);
        inputMap.put(KeyStroke.getKeyStroke(J_RELEASED), J_RELEASED);
        inputMap.put(KeyStroke.getKeyStroke(K_RELEASED), K_RELEASED);
        inputMap.put(KeyStroke.getKeyStroke(L_RELEASED), L_RELEASED);
        inputMap.put(KeyStroke.getKeyStroke(Z_RELEASED), Z_RELEASED);
        inputMap.put(KeyStroke.getKeyStroke(X_RELEASED), X_RELEASED);
        inputMap.put(KeyStroke.getKeyStroke(C_RELEASED), C_RELEASED);
        inputMap.put(KeyStroke.getKeyStroke(V_RELEASED), V_RELEASED);
        inputMap.put(KeyStroke.getKeyStroke(B_RELEASED), B_RELEASED);
        inputMap.put(KeyStroke.getKeyStroke(N_RELEASED), N_RELEASED);
        inputMap.put(KeyStroke.getKeyStroke(M_RELEASED), M_RELEASED);
        inputMap.put(KeyStroke.getKeyStroke(SHIFT_RELEASED), SHIFT_RELEASED);
        inputMap.put(KeyStroke.getKeyStroke(CTRL_RELEASED), CTRL_RELEASED);
        inputMap.put(KeyStroke.getKeyStroke(ONE_RELEASED), ONE_RELEASED);
        inputMap.put(KeyStroke.getKeyStroke(TWO_RELEASED), TWO_RELEASED);
        inputMap.put(KeyStroke.getKeyStroke(THREE_RELEASED), THREE_RELEASED);
        inputMap.put(KeyStroke.getKeyStroke(FOUR_RElEASED), FOUR_RElEASED);
        inputMap.put(KeyStroke.getKeyStroke(FIVE_RELEASED), FIVE_RELEASED);
        inputMap.put(KeyStroke.getKeyStroke(SIX_RELEASED), SIX_RELEASED);
        inputMap.put(KeyStroke.getKeyStroke(SEVEN_RELEASED), SEVEN_RELEASED);
        inputMap.put(KeyStroke.getKeyStroke(EIGHT_RELEASED), EIGHT_RELEASED);
        inputMap.put(KeyStroke.getKeyStroke(NINE_RELEASED), NINE_RELEASED);
    }

    private void addActions(JComponent component) {
        ActionMap actionMap = component.getActionMap();

        actionMap.put(UP_PRESSED, new PressAction(UP));
        actionMap.put(DOWN_PRESSED, new PressAction(DOWN));
        actionMap.put(LEFT_PRESSED, new PressAction(LEFT));
        actionMap.put(RIGHT_PRESSED, new PressAction(RIGHT));
        actionMap.put(ESCAPE_PRESSED, new PressAction(ESCAPE));
        actionMap.put(ENTER_PRESSED, new PressAction(ENTER));
        actionMap.put(Q_PRESSED, new PressAction(Q));
        actionMap.put(W_PRESSED, new PressAction(W));
        actionMap.put(E_PRESSED, new PressAction(E));
        actionMap.put(R_PRESSED, new PressAction(R));
        actionMap.put(T_PRESSED, new PressAction(T));
        actionMap.put(Y_PRESSED, new PressAction(Y));
        actionMap.put(U_PRESSED, new PressAction(U));
        actionMap.put(I_PRESSED, new PressAction(I));
        actionMap.put(O_PRESSED, new PressAction(O));
        actionMap.put(P_PRESSED, new PressAction(P));
        actionMap.put(A_PRESSED, new PressAction(A));
        actionMap.put(S_PRESSED, new PressAction(S));
        actionMap.put(D_PRESSED, new PressAction(D));
        actionMap.put(F_PRESSED, new PressAction(F));
        actionMap.put(G_PRESSED, new PressAction(G));
        actionMap.put(H_PRESSED, new PressAction(H));
        actionMap.put(J_PRESSED, new PressAction(J));
        actionMap.put(K_PRESSED, new PressAction(K));
        actionMap.put(L_PRESSED, new PressAction(L));
        actionMap.put(Z_PRESSED, new PressAction(Z));
        actionMap.put(X_PRESSED, new PressAction(X));
        actionMap.put(C_PRESSED, new PressAction(C));
        actionMap.put(V_PRESSED, new PressAction(V));
        actionMap.put(B_PRESSED, new PressAction(B));
        actionMap.put(N_PRESSED, new PressAction(N));
        actionMap.put(M_PRESSED, new PressAction(M));
        actionMap.put(SHIFT_PRESSED, new PressAction(SHIFT));
        actionMap.put(CTRL_PRESSED, new PressAction(CTRL));
        actionMap.put(ONE_PRESSED, new PressAction(ONE));
        actionMap.put(TWO_PRESSED, new PressAction(TWO));
        actionMap.put(THREE_PRESSED, new PressAction(THREE));
        actionMap.put(FOUR_PRESSED, new PressAction(FOUR));
        actionMap.put(FIVE_PRESSED, new PressAction(FIVE));
        actionMap.put(SIX_PRESSED, new PressAction(SIX));
        actionMap.put(SEVEN_PRESSED, new PressAction(SEVEN));
        actionMap.put(EIGHT_PRESSED, new PressAction(EIGHT));
        actionMap.put(NINE_PRESSED, new PressAction(NINE));

        actionMap.put(UP_RELEASED, new ReleaseAction(UP));
        actionMap.put(DOWN_RELEASED, new ReleaseAction(DOWN));
        actionMap.put(LEFT_RELEASED, new ReleaseAction(LEFT));
        actionMap.put(RIGHT_RELEASED, new ReleaseAction(RIGHT));
        actionMap.put(ESCAPE_RELEASED, new ReleaseAction(ESCAPE));
        actionMap.put(ENTER_RELEASED, new ReleaseAction(ENTER));
        actionMap.put(Q_RELEASED, new ReleaseAction(Q));
        actionMap.put(W_RELEASED, new ReleaseAction(W));
        actionMap.put(E_RELEASED, new ReleaseAction(E));
        actionMap.put(R_RELEASED, new ReleaseAction(R));
        actionMap.put(T_RELEASED, new ReleaseAction(T));
        actionMap.put(Y_RELEASED, new ReleaseAction(Y));
        actionMap.put(U_RELEASED, new ReleaseAction(U));
        actionMap.put(I_RELEASED, new ReleaseAction(I));
        actionMap.put(O_RELEASED, new ReleaseAction(O));
        actionMap.put(P_RELEASED, new ReleaseAction(P));
        actionMap.put(A_RELEASED, new ReleaseAction(A));
        actionMap.put(S_RELEASED, new ReleaseAction(S));
        actionMap.put(D_RELEASED, new ReleaseAction(D));
        actionMap.put(F_RELEASED, new ReleaseAction(F));
        actionMap.put(G_RELEASED, new ReleaseAction(G));
        actionMap.put(H_RELEASED, new ReleaseAction(H));
        actionMap.put(J_RELEASED, new ReleaseAction(J));
        actionMap.put(K_RELEASED, new ReleaseAction(K));
        actionMap.put(L_RELEASED, new ReleaseAction(L));
        actionMap.put(Z_RELEASED, new ReleaseAction(Z));
        actionMap.put(X_RELEASED, new ReleaseAction(X));
        actionMap.put(C_RELEASED, new ReleaseAction(C));
        actionMap.put(V_RELEASED, new ReleaseAction(V));
        actionMap.put(B_RELEASED, new ReleaseAction(B));
        actionMap.put(N_RELEASED, new ReleaseAction(N));
        actionMap.put(M_RELEASED, new ReleaseAction(M));
        actionMap.put(SHIFT_RELEASED, new ReleaseAction(SHIFT));
        actionMap.put(CTRL_RELEASED, new ReleaseAction(CTRL));
        actionMap.put(ONE_RELEASED, new ReleaseAction(ONE));
        actionMap.put(TWO_RELEASED, new ReleaseAction(TWO));
        actionMap.put(THREE_RELEASED, new ReleaseAction(THREE));
        actionMap.put(FOUR_RElEASED, new ReleaseAction(FOUR));
        actionMap.put(FIVE_RELEASED, new ReleaseAction(FIVE));
        actionMap.put(SIX_RELEASED, new ReleaseAction(SIX));
        actionMap.put(SEVEN_RELEASED, new ReleaseAction(SEVEN));
        actionMap.put(EIGHT_RELEASED, new ReleaseAction(EIGHT));
        actionMap.put(NINE_RELEASED, new ReleaseAction(NINE));
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // not needed
    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (e.getButton()) {
            case MouseEvent.BUTTON1:
                InputHandler.setKey(MOUSE_LEFT, true);
                break;
            case MouseEvent.BUTTON2:
                InputHandler.setKey(SCROLL_WHEEL, true);
                break;
            case MouseEvent.BUTTON3:
                InputHandler.setKey(MOUSE_RIGHT, true);
                break;
            default:
                break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (e.getButton()) {
            case MouseEvent.BUTTON1:
                InputHandler.setKey(MOUSE_LEFT, false);
                break;
            case MouseEvent.BUTTON2:
                InputHandler.setKey(SCROLL_WHEEL, false);
                break;
            case MouseEvent.BUTTON3:
                InputHandler.setKey(MOUSE_RIGHT, false);
                break;
            default:
                break;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        mouseInsideWindow = true;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        mouseInsideWindow = false;
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    public static boolean isMouseInsideWindow() {
        return mouseInsideWindow;
    }

    public static int getMouseX() {
        return mouseX;
    }

    public static int getMouseY() {
        return mouseY;
    }

    public static int getScroll() {
        return scroll;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        scroll = e.getWheelRotation();
    }

    public static boolean mouseInside(Rectangle rectangle) {
        return rectangle.contains(mouseX, mouseY);
    }

    private static class ReleaseAction extends AbstractAction {

        private int keycode;

        public ReleaseAction(int keycode) {
            super();
            this.keycode = keycode;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            InputHandler.setKey(keycode, false);
        }

    }

    private class PressAction extends AbstractAction {

        private int keycode;

        public PressAction(int keycode) {
            super();
            this.keycode = keycode;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            InputHandler.setKey(keycode, true);
        }

    }

}
