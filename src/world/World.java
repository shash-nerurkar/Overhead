package world;

import essentials101.Shader;
import essentials101.Animation;
import essentials101.Camera;
import essentials101.Window;

import java.nio.DoubleBuffer;
import java.util.ArrayList;
import java.util.List;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import collision.AABB;
import entity.Entity;
import entity.Player;
import entity.Transform;

import static org.lwjgl.glfw.GLFW.*;

public class World {
	private final int view = 24;
	private byte[] tiles;
	private int width;
	private int height;
	private int scale;
	private Matrix4f world;
	private AABB[] bounding_boxes;
	private List<Entity> entities;
	/*
	public World(String world) {
		try{
			
			BufferedImage tile_sheet = ImageIO.read(new File("./res/levels/" + world + "/tiles.png"));
			//BufferedImage entity_sheet = ImageIO.read(new File("./res/levels/" + world + "/entities.png"));
			
			this.width = tile_sheet.getWidth();
			this.height = tile_sheet.getHeight();
			this.scale = 25;
			
			int[] colorTileSheet = tile_sheet.getRGB(0, 0, width, height, null, 0, width);
			//int[] colorEntitySheet = entity_sheet.getRGB(0, 0, width, height, null, 0, width);
			
			this.world = new Matrix4f().setTranslation(new Vector3f(0));
			this.world.scale(this.scale);
			this.tiles = new byte[width * height];
			this.bounding_boxes = new AABB[width * height];
			
			entities = new ArrayList<Entity>();
		    
			//Transform transform;
			
			for(int y=0; y<height;y++) {
				for(int x=0; x<width; x++) {
					int red = (colorTileSheet[x+ y*width] >> 16) & 0xFF;					
				//	int entity_index = (colorEntitySheet[x + y*width] >> 16) & 0xFF;
				//	int entity_alpha = (colorEntitySheet[x + y*width] >> 24) & 0xFF;
					
					Tile t;
					try {
						t = Tile.tiles[red];
					}catch(ArrayIndexOutOfBoundsException e) {
						t = null;
					}
					
					if(t != null)
						this.setTile(t, x, y);
				/*
					if(entity_alpha > 0) {
						transform = new Transform();
						transform.pos.x = x;
						transform.pos.y = -y;	
						switch(entity_index) {
						case 1: 
							Player player = new Player(transform);
							entities.add(player);
							break;
						default:
							
							break;
						}
					}
				}
			}
		}catch(IOException e) {
			e.printStackTrace();
		}/*
		try(MemoryStack stack=MemoryStack.stackPush()) {
			IntBuffer comp=stack.mallocInt(1);
			IntBuffer w=stack.mallocInt(1);
			IntBuffer h=stack.mallocInt(1);
			
			ByteBuffer tile_sheet = STBImage.stbi_load("./res/levels/" + world + "/tiles.png",w,h,comp,4); 	
			if(tile_sheet == null) {
				System.out.println("COULDN'T LOAD " + world);
			}
			
			this.width = w.get();
			this.height = h.get();
			this.scale = 25;
			this.world = new Matrix4f().setTranslation(new Vector3f(0));
			this.world.scale(this.scale);
		    this.tiles = new byte[width * height];
		    bounding_boxes = new AABB[width *height];
		    entities = new ArrayList<Entity>();
		    
			for(int y=0; y<height;y++) {
				for(int x=0; x<width; x++) {
					int red = tile_sheet.get((x+ y*width>>16) & 0xFF);					
					Tile t;
					try {
						t = Tile.tiles[red];
					}
					catch(ArrayIndexOutOfBoundsException e) {
						t = null;
					}
					if(t != null)
						this.setTile(t, x, y);
				}
			}
		}
			entities.add(new Player(new Transform())); 	
			
			//int[] colorSheet = tile_sheet.
//			IntBuffer comp=stack.mallocInt(1);
//			IntBuffer w=stack.mallocInt(1);
//			IntBuffer h=stack.mallocInt(1);
		//	ByteBuffer entity_sheet = STBImage.stbi_load("./res/levels/" + world + "_tiles.png",w,h,comp,4); 	
		 //   if(entity_sheet == null) {
		    //	System.out.println("COULDN'T LOAD " + world);
		   // }			
		//}
	}
	*/
	public World(int width, int height, int scale) {	 
		this.width = width;
		this.height = height;
		this.scale = scale; 
		this.tiles = new byte[width*height];
		this.bounding_boxes = new AABB[width * height];
		this.world = new Matrix4f().setTranslation(new Vector3f(0));
		this.world.scale(this.scale);
		entities = new ArrayList<Entity>();
		entities.add(new Player(new Transform())); 	    
		/*
		Transform t = new Transform();
		t.pos.x = 5 * 2;
		t.pos.y = 4 * 2;
		
		final int bot1anim = 0;
		bot1.setAnimation(bot1anim, new Animation(4, 60, "player/walking"));
		entities.add(new Entity(4, t));
		*/
	}
	
	public static double getCursorPosX(long windowID) {
	    DoubleBuffer posX = BufferUtils.createDoubleBuffer(1);
	    glfwGetCursorPos(windowID, posX, null);
	    return posX.get(0);
	}
	
	public static double getCursorPosY(long windowID) {
	    DoubleBuffer posY = BufferUtils.createDoubleBuffer(1);
	    glfwGetCursorPos(windowID, null, posY);
	    return posY.get(0);
	}
	
	public void render(TileRenderer render, Shader shader, Camera cam, Window window) {
		int posX =  ((int)cam.getPosition().x + (window.getWidth()/2))/ (this.scale * 2);
		int posY =  ((int)cam.getPosition().y - (window.getHeight()/2))/ (this.scale * 2);
		
		for(int i = 0; i<view; i++) {
			for(int j = 0; j<view; j++) { 
				Tile t = this.getTile(i-posX, j+posY);
				if(t != null)
					render.renderTile(t, i-posX, -j-posY, shader, this.world, cam);
			}
		}
		
		for(Entity entity: entities) {
			entity.render(shader, cam, this);
		}
	
	}
	
	public void update(float delta, Window window, Camera camera) {
		for(Entity entity: entities) {
			entity.update(delta, window, camera, this);	
		}
		
		for(int i=0; i<entities.size(); i++) {
			entities.get(i).collideWithTiles(this);
			for(int j=i+1; j<entities.size(); j++) {
				entities.get(i).collideWithEntity(entities.get(j));
			}
			entities.get(i).collideWithTiles(this);
		}
	}
	
	public void correctCamera(Camera camera, Window window) {
		Vector3f pos = camera.getPosition();
		
		int w = - width * scale * 2;
		int h = height * scale * 2;
		
		if(pos.x > -(window.getWidth()/2) + scale)
			pos.x = -(window.getWidth()/2) + scale;
		if(pos.x < w + (window.getWidth()/2) + scale)
			pos.x = w + (window.getWidth()/2) + scale;
		         
		if(pos.y < (window.getWidth()/2) - scale)
			pos.y = (window.getWidth()/2) - scale;
		if(pos.y > h - (window.getWidth()/2) - scale)	
			pos.y = h -(window.getWidth()/2) - scale;
		
		if(pos.y < (window.getWidth()/2) + scale)
			pos.y = (window.getWidth()/2) + scale;
		if(pos.y > h - (window.getWidth()/2) + scale)	
			pos.y = h -(window.getWidth()/2) + scale;
	}	
	
	public void setTile(Tile tile, int x, int y) {
		this.tiles[x+ y*width] = tile.getId();
		if(tile.isSolid())
			this.bounding_boxes[x + y*width] = new AABB(new Vector2f(x*2, -y*2), new Vector2f(1,1));
		else
			bounding_boxes[x + y*width] = null;
	}

	public Tile getTile(int x,int y) {
		try {
			return Tile.tiles[tiles[x + y * width]];
		}catch(ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}

	public AABB getTileBoundingBox(int x,int y) {
		try {
			return this.bounding_boxes[x + y * width];
		}catch(ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}
	
	public Matrix4f getWorldMatrix() { return this.world; }
	public int getScale() { return scale; }
}
