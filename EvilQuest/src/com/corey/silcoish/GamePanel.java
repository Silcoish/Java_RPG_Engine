package com.corey.silcoish;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import com.corey.silcoish.gamestate.GameStateManager;

public class GamePanel extends JPanel implements Runnable, KeyListener {
	private static final long serialVersionUID = 1L;

	public static final int WIDTH = 400;
	public static final int HEIGHT = WIDTH / 3 * 2;
	private static final int SCALE = 2;

	public Thread thread;
	private boolean running;
	
	private int frames, updates;

	private BufferedImage image;
	private Graphics g;

	public GameStateManager gsm;

	public GamePanel() {
		super();
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setFocusable(true);
		requestFocus();
	}

	public void addNotify() {
		super.addNotify();
		if (thread == null) {
			thread = new Thread(this);
			addKeyListener(this);
			thread.start();
		}
	}

	private void init() {
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = image.getGraphics();

		running = true;

		gsm = new GameStateManager();
	}

	private void update() {
		gsm.update();
	}

	private void render() {
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = image.getGraphics();
		gsm.render(g);
	}

	private void renderToScreen() {
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
	}

	public void keyPressed(KeyEvent k) {
		gsm.keyPressed(k.getKeyCode());
	}

	public void keyReleased(KeyEvent k) {
		gsm.keyReleased(k.getKeyCode());
	}

	public void keyTyped(KeyEvent arg0) {
	}

	//The main game loop
	public void run() {
		init();
		requestFocus();
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;
		frames = 0;
		updates = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				update();
				updates++;
				delta--;
			}
			render();
			renderToScreen();
			frames++;

			if (System.currentTimeMillis() - timer >= 1000) {
				timer += 1000;
				System.err.println("UPS: " + updates + ", FPS: " + frames);
				updates = 0;
				frames = 0;
			}
		}

		System.exit(0);
	}

}
