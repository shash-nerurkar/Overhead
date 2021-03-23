package game;

import essentials101.*;
import entity.Entity;
	
import org.lwjgl.opengl.*;

import world.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.joml.*;

class Main {

	 void makeWindow() {

		 float[] oCoord= {
			-.90f,.90f,
			-.75f,.85f,
			-.47f,.90f,
			-.62f,.85f,
			-.62f,.09f,
			-.47f,.04f,
			-.90f,.04f,
			-.75f,.09f
		 };
		 int[] oIndices= {
				 0,1,2,
				 1,2,3,
				 2,3,4,
				 2,4,5,
				 4,5,6,
				 4,6,7,
				 6,7,0,
				 0,1,7
		 };
		 
		 float[] vCoord= {
				 -.43f,.90f,
				 -.27f,.90f,
				 -.23f,.09f,
				 -.19f,.90f,
				 -.04f,.90f,
				 -.08f,.04f,
				 -.38f,.04f,
		 };
		 int[] vIndices= {
			0,1,2,
			0,2,6,
			2,6,5,
			2,3,4,
			2,4,5
		 };
		 
		 
		 float[] eCoord= {
		     .04f,0.90f,
		     .43f,.90f,
		     .43f,.85f,
		     .19f,.85f,
		     .19f,.50f,
		     .43f,.50f,
		     .43f,.45f,
		     .19f,.45f,
		     .19f,.09f,
		     .43f,.09f,
		     .43f,.04f,
		     .04f,.04f
		 };
		 int[] eIndices= {
			0,1,2,
			0,2,3,
			0,3,8,
			0,11,8,
			4,5,6,
			4,6,7,
			8,9,10,
			8,10,11
		 };
		 
		 
		 float[] rCoord= {
			.47f,.90f,
			.90f,.90f,
			.90f,.51f,
			.82f,.51f,
			.90f,.04f,
			.75f,.04f,
			.67f,.51f,
			.62f,.51f,
			.62f,.04f,
			.47f,.04f,
			.62f,.85f,
			.85f,.85f,
			.85f,.56f,
			.62f,.56f
		 };
		 int[] rIndices= {
			0,1,10,
			1,10,11,
			1,11,12,
			1,2,12,
			2,12,13,
			2,3,13,
			0,8,10,
			0,8,9,
			3,7,13,
			3,6,7,
			3,5,6,
			3,4,5
		 };
		 
		 
		 float[] hCoord= {
				-.90f,-.04f,
				-.75f,-.04f,
				-.75f,-.45f,
				-.62f,-.45f,
				-.62f,-.04f,
				-.47f,-.04f,
				-.47f,-.90f,
				-.62f,-.90f,
				-.62f,-.50f,
				-.75f,-.50f,
				-.75F,-.90F,
				-.90f,-.90f			
		 };
		 int[] hIndices= {
				0,1,10,
				0,11,10,
				2,3,8,
				2,9,8,
				4,5,6,
				4,7,6
		 };
		 
		 float[] edashCoord= {
			     -.43f,-.04f,
			     -.04f,-.04f,
			     -.04f,-.09f,
			     -.28f,-.09f,
			     -.28f,-.44f,
			     -.04f,-.44f,
			     -.04f,-.49f,
			     -.28f,-.49f,
			     -.28f,-.85f,
			     -.04f,-.85f,
			     -.04f,-.90f,
			     -.43f,-.90f
		 };
		 
		float[] aCoord= {
				 .08f,-.04f,
				 .38f,-.04f,
				 .43f,-.90f,
				 .28f,-.90f,
				 .26f,-.52f,
				 .21f,-.52f,
				 .19f,-.90f,
				 .04f,-.90f,
				 .235f,-.09f,
				 .26f,-.47f,
				 .21f,-.47f
		 };
		 int[] aIndices= {
				 6,0,7,
				 6,0,8,
				 1,0,8,
				 1,3,8,
				 1,3,2,
				 5,9,4,
				 5,9,10
		 };
		 
		 float[] dCoord= {
				.47f,-.04f,//0
				.62f,-.09f,//4
				.90f,-.04f,//1
				.85f,-.09f,//5
				.85f,-.85f,//6
				.90f,-.90f,//2
				.47f,-.90f,//3
				.62f,-.85f//7
		 };
		 
		 float[] pressCoord = new float[] {
					-.50f,-.80f,
					 .50f,-.80f,
					 .50f,-.90f,
					 -.50f,-.90f
		 };			 
		 int[] pressIndex = new int[] {
				0,1,2,
				2,3,0
		 };			 
		 float[] texture = new float[] {
					0,0,
					1,0,
					1,1,
					0,1
		 };
			 
		 //window
		 Window window = new Window();
		 window.setSize(700,700);//window.getWidth(),window.getHeight());	 
		 //window.setFullscreen(true);
		 window.createWindow("Overhead");
		 
		 GL.createCapabilities();

		 glEnable(GL_BLEND);
		 glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		 //camera
		 Camera camera = new Camera(700,765);
		 glEnable(GL_TEXTURE_2D);
		 
		 //shader
		 Shader textureShader = new Shader("shaders");
		 Shader colorShader = new Shader("shader",true);
		 
		 //texture
		 Animation pressKeyToContinue = new Animation(2,60,"press");
		 //Texture pressKeyToContinue = new Texture("press/0.png");
		 
		 //tile renderer, also contains coordinates
		 TileRenderer tiles = new TileRenderer();
		 
		 //entity initialized
		 Entity.initAsset();
		 
		 //mathematics
		 Matrix4f overheadMat = new Matrix4f().scale(250);
		 Matrix4f pressMat = new Matrix4f().scale(320);
		 
		 //world
		 World world = new World(64,64,20);
		 
		 //za warudo 
		 for(int i=7; i<=25; i++) 
				 world.setTile(Tile.test_tile2, i, 5);
		 
		 for(int j=5; j<=28; j++)
			 world.setTile(Tile.test_tile2, 5, j);
		 
		 for(int i=6; i<=23; i++) 
			 world.setTile(Tile.test_tile2, i, 28);
		 
		 for(int j=6; j<=28; j++)
			 world.setTile(Tile.test_tile2, 25, j);
		
		 for(int i=12; i<=23; i++) {
			 for(int j=7; j<9; j++)
				 world.setTile(Tile.test_tile2, i, j);
		 }
		 
		 for(int i=9; i<12; i++)
			 world.setTile(Tile.test_tile2, 12, i);
		
		 for(int i=19; i<=23; i++) {
			 for(int j=9; j<14; j++) {
				 if(j==11) {
					 world.setTile(Tile.test_tile2, 19, j);
					 world.setTile(Tile.test_tile2, 20, j);
					 continue;
				 }
				 else
				 	world.setTile(Tile.test_tile2, i, j);
			 }
		 }
		 
		 for(int i=14; i<=17; i++) {
			 for(int j=10; j<12; j++) 
				 world.setTile(Tile.test_tile2, i, j);
		 }
		
		 for(int i=16; i<=17; i++) {
			 for(int j=10; j<12; j++) 
				 world.setTile(Tile.test_tile2, i, j);
		 }
		 
		 for(int i=17; i<=25; i++) {
			 for(int j=15; j<18; j++) 
				 world.setTile(Tile.test_tile2, i, j);
		 }
		 
		 for(int i=21; i<=25; i++) {
			 for(int j=19; j<23; j++) 
				 world.setTile(Tile.test_tile2, i, j);
		 }
		 
		 for(int i=19; i<=23; i++) {
			 for(int j=25; j<28; j++) {
				 if(j==25) {
					 world.setTile(Tile.test_tile2, 21, j);
					 world.setTile(Tile.test_tile2, 22, j);
					 world.setTile(Tile.test_tile2, 23, j);
					 continue;
				 }
				 else
				 	world.setTile(Tile.test_tile2, i, j);
			 }
		 }
		 
		 for(int i=7; i<=13; i++) {
			 for(int j=13; j<17; j++)
				 if(j<17 && j>13 && i==10)
					 continue;
				 else
			 		world.setTile(Tile.test_tile2, i, j);
		 }
		 
		 for(int j=14; j<21; j++)
			 world.setTile(Tile.test_tile2, 15, j);
			 
		 for(int i=15; i<=17; i++) {
			 for(int j=12; j<14; j++) 
				 world.setTile(Tile.test_tile2, i, j);
		 }
		 
		 for(int j=17; j<20; j++)
			 world.setTile(Tile.test_tile2, 7, j);
		 
		 for(int i=6; i<=7; i++) {
			 for(int j=21; j<26; j++)
				 world.setTile(Tile.test_tile2, i, j);
		 }	 
		 
		 for(int i=6; i<=17; i++) {
			 for(int j=26; j<28; j++)
				 world.setTile(Tile.test_tile2, i, j);
		 }
		 
		 for(int i=9; i<=14; i++) {
			 for(int j=18; j<21; j++)
				 world.setTile(Tile.test_tile2, i, j);
		 }
	
		 for(int j=18; j<25; j++)
			 world.setTile(Tile.test_tile2, 9, j);
		 
		 for(int j=22; j<25; j++)
			 world.setTile(Tile.test_tile2, 10, j);
		 
		 for(int i=12; i<=20; i++)
			 world.setTile(Tile.test_tile2, i, 22);
		 
		 for(int i=11; i<=23; i++)
			 world.setTile(Tile.test_tile2, i, 24);
		 
		 for(int i=15; i<=19; i++) {
			 for(int j=19; j<21; j++)
				 world.setTile(Tile.test_tile2, i, j);
		 }
		 
		 world.setTile(Tile.test_tile2, 10, 12);
		 
		 world.setTile(Tile.test_tile2, 9, 7);
		 world.setTile(Tile.test_tile2, 10, 7);
		 world.setTile(Tile.test_tile2, 9, 8);
		 world.setTile(Tile.test_tile2, 10, 8);
		 world.setTile(Tile.test_tile2, 10, 9);
		 world.setTile(Tile.test_tile2, 10, 10);
		 world.setTile(Tile.test_tile2, 10, 11);
		 
		 /*
		  for(int i=6; i<=7; i++) {
		  	for(int j=7; j<12; j++)
		  	 	world.setTile(Tile.test_tile, i, j);
		  }
		  */
		 
		 world.setTile(Tile.test_tile2, 6, 7);
		 world.setTile(Tile.test_tile2, 7, 7);
		 world.setTile(Tile.test_tile2, 6, 8);
		 world.setTile(Tile.test_tile2, 7, 8);
		 world.setTile(Tile.test_tile2, 6, 9);
		 world.setTile(Tile.test_tile2, 7, 9);
		 world.setTile(Tile.test_tile2, 6, 10);
		 world.setTile(Tile.test_tile2, 7, 10);
		 world.setTile(Tile.test_tile2, 8, 10);
		 world.setTile(Tile.test_tile2, 6, 11);
		 world.setTile(Tile.test_tile2, 7, 11);
		 world.setTile(Tile.test_tile2, 8, 11);
		 
		 for(int i=13; i<=20; i++)
			 world.setTile(Tile.test_tile2, i, 8);
		 
		 //main menu models
		 Model oModel = new Model(oCoord,texture,oIndices); 
		 Model vModel = new Model(vCoord,texture,vIndices); 
		 Model eModel = new Model(eCoord,texture,eIndices); 
		 Model rModel = new Model(rCoord,texture,rIndices);
		 Model hModel = new Model(hCoord,texture,hIndices); 
		 Model edashModel = new Model(edashCoord,texture,eIndices); 
		 Model aModel = new Model(aCoord,texture,aIndices);
		 Model dModel = new Model(dCoord,texture,oIndices);
		 Model pressKey = new Model(pressCoord,texture,pressIndex);
		 
		 //photo synthesizers
		 float red = 1;
		 float green = 0;
		 float blue = 0;
		 float opacity = 1;
		 boolean checker = false;
		 
		 //time controllers
		 double frame_cap = 1.0f/60.0f;
		 double frame_time = 0;
		 int frames = 0;
		 double time = Timer.getTime();
		 double unprocessed = 0;
		 		 
		 while(!window.shouldClose()) {
			 
				 boolean can_Render = false;
				 double time_2 = Timer.getTime();
				 double passed = time_2 - time;
				 unprocessed += passed;
				 frame_time += passed;
				 time = time_2;
				 
				 while(unprocessed >= frame_cap) {
					 unprocessed -= frame_cap;
					 can_Render = true;	 
					 if(window.getInput().isKeyPressed(GLFW_KEY_ENTER))
						 checker = true;
				 }

				 if(frame_time >= 1.0) {
						 frame_time = 0;
						 System.out.println("FPS: " + frames);
						 frames = 0;
				 }
				 
				 
				 if(can_Render) { 
					 	window.update();
					
					 	glClear(GL_COLOR_BUFFER_BIT);
					 	
						glClearColor(0,0,0,1);
						colorShader.bind();
						colorShader.setUniform("red", red);
						colorShader.setUniform("green", green);
						colorShader.setUniform("blue", blue);
						colorShader.setUniform("opacity", opacity);
						colorShader.setUniform("projection", camera.getProjection().mul(overheadMat));
	 
						oModel.render();
						vModel.render();
						eModel.render();
						rModel.render();
						hModel.render();
						edashModel.render();
						aModel.render();
						dModel.render();
						 
						/*
						 pressKeyToContinue.bind(0);
						 textureShader.bind();
						 textureShader.setUniform("sampler", 0);
						 textureShader.setUniform("projection", camera.getProjection().mul(pressMat));
						 pressKey.renderTexture();

						 pressMat.scale(320);*/
						 if(checker) {
							
							 
							 world.update((float)frame_cap, window, camera);
							 world.correctCamera(camera, window);
							 overheadMat.scale(0);
							 world.render(tiles, textureShader, camera, window);
							
						 }
						 window.swapBuffers();
						 frames++;
				 }		 
		}
		 
		 Entity.deleteAsset();
		 window.closeWindow();
	}
}

public class Overhead {
	public static void main(String[] args) {
		new Main().makeWindow();
	}	
}
