package application.core;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.image.Image;

public class Dealer {
	protected ArrayList<Tile> deck;
	protected ArrayList<Tile> deck2;
	protected boolean useDeck1 = false;
	protected int deckPointer;
	protected Random r;
	protected Tile x, y;
	protected int randomIndex;
	protected Image[][][][] images = new Image[3][3][3][3];
	protected boolean rigged;
	
	public Dealer(boolean Rigged) {
		rigged = Rigged;
		System.out.println("Initializing Dealer...");
		deck = new ArrayList<Tile>(81);
		deck2 = new ArrayList<Tile>(81);
		deckPointer = 0;
		r = new Random();
		
		for (int shape = 1; shape < 4; ++shape)
        {
            for (int color = 1; color < 4; ++color)
            {
                for (int number = 1; number < 4; ++number)
                {
                    for (int shading = 1; shading < 4; ++shading)
                    {
                    	try {
                    		images[shape-1][color-1][number-1][shading-1] = new Image("BeSetArt/Images/BeSetArt" + 
                            		Tile.toColorString(color) + Tile.toShadingString(shading) + "_" + 
                            		Tile.toNumberString(number) + Tile.toSymbolString(shape) + "Card.png");
                    	} catch (Exception e) {
                    		System.out.println("Error loading BeSetArt/Images/BeSetArt" + 
                            		Tile.toColorString(color) + Tile.toShadingString(shading) + "_" + 
                            		Tile.toNumberString(number) + Tile.toSymbolString(shape) + "Card.png");
                    		images[shape-1][color-1][number-1][shading-1] = Tile.Undefined.getTexture();
                    	}
                        
                    }
                }
            }
        }
		
		reset();
	}
	
	public void reset() {
		useDeck1 = true;
		deck.clear();
		deck2.clear();
		
		for (int shape = 1; shape < 4; ++shape)
        {
            for (int color = 1; color < 4; ++color)
            {
                for (int number = 1; number < 4; ++number)
                {
                    for (int shading = 1; shading < 4; ++shading)
                    {
                        deck.add(new Tile(shape, color, number, shading, images[shape-1][color-1][number-1][shading-1]));
                    }
                }
            }
        }
        shuffle();
	}
	
	public void shuffle() {
		//If using deck 1, shuffle deck 1, else shuffle deck 2
		if (useDeck1) {
			deck2.clear();
			for (int cardIndex = deck.size() - 1; cardIndex > 0; --cardIndex)
        	{
            	randomIndex = r.nextInt(cardIndex + 1);
            	x = deck.get(cardIndex);
            	y = deck.get(randomIndex);
            	deck.set(cardIndex, y);
            	deck.set(randomIndex, x);
        	}
		} else {
			deck.clear();
			for (int cardIndex = deck.size() - 1; cardIndex > 0; --cardIndex)
        	{
            	randomIndex = r.nextInt(cardIndex + 1);
            	x = deck2.get(cardIndex);
            	y = deck2.get(randomIndex);
            	deck2.set(cardIndex, y);
            	deck2.set(randomIndex, x);
        	}
		}
        deckPointer = 0;
	}
	
	public Tile deal() {
		//If the active deck has a tile available, deal it, else switch the active deck, shuffle it, and deal from that
		if (deckPointer < deck.size()) {
			++deckPointer;
			if (useDeck1) {
				return deck.get(deckPointer - 1);
			} else {
				return deck2.get(deckPointer - 1);
			}
		} else {
			useDeck1 = !useDeck1;
			shuffle();
			return deal();
		}
	}
	
	public void discard(Tile t) {
		//Add t to the inactive deck
		if (!useDeck1) {
			deck2.add(t);
		} else {
			deck.add(t);
		}
	}
	
	public boolean isRigged() {
		return rigged;
	}
}
