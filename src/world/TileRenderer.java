package world;

import  java.util.HashMap;

import org.joml.Matrix4f;
import org.joml.Vector3f;

//user-defined
import essentials101.*;  

public class TileRenderer {
	private HashMap<String, Texture> tile_textures;
	private Model model;
	
	public TileRenderer() {
		tile_textures = new HashMap<String, Texture>();
		float[] vertices = new float[] {
			-1f,  1f,
			 1f,  1f,
			 1f, -1f,
			-1f, -1f
		};
		
		int[] indices = new int[] {
			0,2,1,
			2,3,0
		};
		
		float[] texture = new float[] {
				 0,0,
				 1,0,
				 1,1,
				 0,1
		 };
		 
		this.model = new Model(vertices,texture,indices);
		
		
		 for(int i=0;i< Tile.tiles.length; i++) {
			 if(Tile.tiles[i] != null) {
				 if(!tile_textures.containsKey(Tile.tiles[i].getTexture())) {
					 String tex = Tile.tiles[i].getTexture();
				 	 tile_textures.put( tex, new Texture(tex + ".png"));
				 }                                         
			 }
		 }				 
	}
	
	public void renderTile(Tile tile,int x, int y, Shader shader, Matrix4f world, Camera cam) {

		 shader.bind();
			 
		 if(tile_textures.containsKey(tile.getTexture())) 
			 tile_textures.get(tile.getTexture()).bind(0);
			 
		 Matrix4f tile_pos = new Matrix4f().translate( new Vector3f(x*2, y*2, 0));
		 Matrix4f target = new Matrix4f();
			 
		 cam.getProjection().mul(world, target);
		 target.mul(tile_pos);
			 
		 shader.setUniform("sampler", 0);
		 shader.setUniform("projection", target);
			 
		 model.renderTexture();
	 }
	
	 public void dontDo(byte flag) {
		byte i=0;

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
		 
		 float[] texture = new float[] {
				 0,0,
				 1,0,
				 1,1,
				 0,1
		 };
		 
	switch(flag) {
	 case 'o':
		 model = new Model(oCoord,texture,oIndices); 
		 break;
	 case 'v':
		 model = new Model(vCoord,texture,vIndices); 
		 break;
	 case 'e':
		 model = new Model(eCoord,texture,eIndices); 
		 break;
	 case 'r':
		 model = new Model(rCoord,texture,rIndices); 
		 break;
	 case 'h':
		 model = new Model(hCoord,texture,hIndices); 
		 break;
	 case 'f':
		 model = new Model(edashCoord,texture,eIndices); 
		 break;
	 case 'a':
		 model = new Model(aCoord,texture,aIndices); 
		 break;
	 case 'd':
		 model = new Model(dCoord,texture,oIndices);
	 }
	}
}
