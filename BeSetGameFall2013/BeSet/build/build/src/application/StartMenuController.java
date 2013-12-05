/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License"). You
 * may not use this file except in compliance with the License. You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

/*
 * "Portions Copyright 2013 Clinton Walker"
 * */

package application;

/**
 * @author clintonwalker
 * modifed from original
 * */


import java.net.URL;
import java.util.ResourceBundle;

import application.ControlledScreen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class StartMenuController implements Initializable, ControlledScreen {

	ScreensController myController;

	@FXML		Pane			startMenu;
	@FXML		ImageView		startMenuBackGround;
	@FXML		ImageView		besetLogo;
	@FXML		Button			startBtn;
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO
	}

	@Override
	public void setScreenParent(ScreensController screenPage) {
		System.out.println("setScreenParent start menu");
		myController = screenPage;
		Main.SMC = this;

	}

	@FXML
	private void playSet(ActionEvent event){
		Main.GBC.progress = 1.0;
		Main.DEMO = true;
		System.out.println("start game method load gameboard screen");
		boolean loaded = myController.setScreen(Main.GAMEBOARD);
	}
	
	@FXML
	private void playSetDemo(ActionEvent event){
		Main.GBC.progress = 1.0;
		
		System.out.println("start game method load gameboard screen");
		boolean loaded = myController.setScreen(Main.GAMEBOARD);
	}
	
	private void resumeGame(ActionEvent event){
		boolean loaded = myController.setScreen(Main.GAMEBOARD);
		Main.PAUSE = false;
	}
}