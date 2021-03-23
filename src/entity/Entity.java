package entity;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import collision.AABB;
import collision.Collision;
import essentials101.Animation;
import essentials101.Camera;
import essentials101.Model;
import essentials101.Shader;
import essentials101.Window;
import world.World;

public abstract class Entity {

	private static Model model;
	protected Transform transform;
	protected Animation[] animations;
	private int use_animation;
	
	protected AABB bounding_box;
	
	public Entity(int max_animations, Transform transform) {
		this.animations = new Animation[max_animations];
		this.transform = transform;
		this.use_animation = 0;
		this.bounding_box = new AABB(new Vector2f(transform.pos.x,transform.pos.y), new Vector2f(transform.scale.x, transform.scale.y));
	}	
	
	public void setAnimation(int index, Animation animation) {
		animations[index] = animation;
	}
	
	public void useAnimation(int index) {
		this.use_animation = index;
	}
	
	public void move(Vector2f direction) {
		transform.pos.add(new Vector3f(direction, 0));
		
		bounding_box.getCenter().set(transform.pos.x, transform.pos.y);
	}
	
	public void collideWithTiles(World world) {
		AABB[] boxes = new AABB[25];
		 for(int i=0; i<5; i++) {
			 for(int j=0; j<5 ; j++) { 
				 boxes[i + j*5] = world.getTileBoundingBox((int)( ((transform.pos.x/2) + 0.5f) - (5/2) ) + i,
						 								   (int)( ((-transform.pos.y/2) + 0.5f) - (5/2) ) + j);
		 	}
		 }
		 
		 AABB box = null;
		 for(int i=0 ; i<boxes.length; i++) { 
			 if(boxes[i] != null) {
				 if(box == null)
					 box = boxes[i];
				 
				 Vector2f length1 = box.getCenter().sub(transform.pos.x, transform.pos.y, new Vector2f());
				 Vector2f length2 = boxes[i].getCenter().sub(transform.pos.x, transform.pos.y, new Vector2f());
			 
				 if(length1.lengthSquared() > length2.lengthSquared())
					 box = boxes[i];
			 }
		 }
		 
		 if(box != null) {
			 Collision data = bounding_box.getCollision(box);
			 if(data.isIntersecting) {
				 bounding_box.correctPosition(box, data);
			 	 transform.pos.set(bounding_box.getCenter(), 0);
			 }
			 
			 for(int i=0 ; i<boxes.length; i++) {
				 if(boxes[i] != null) {
					 if(box == null)
						 box = boxes[i];
					 
					 Vector2f length1 = box.getCenter().sub(transform.pos.x, transform.pos.y, new Vector2f());
					 Vector2f length2 = boxes[i].getCenter().sub(transform.pos.x, transform.pos.y, new Vector2f());
				 
					 if(length1.lengthSquared() > length2.lengthSquared())
						 box = boxes[i];
				 }
			 }
       	 
			 data = bounding_box.getCollision(box);
			 if(data.isIntersecting) {
				 bounding_box.correctPosition(box, data);
			 	 transform.pos.set(bounding_box.getCenter(), 0);
		 	 }
		}
	}
	
	public void render(Shader shader, Camera camera, World world) {
		Matrix4f target = camera.getProjection();
		target.mul(world.getWorldMatrix());
		
		shader.bind();
		shader.setUniform("sampler", 0);
		shader.setUniform("projection", transform.getProjection(target));
		animations[use_animation].bind(0);
		model.renderTexture();
	}
	
	public abstract void update(float delta, Window window, Camera camera, World world);
	
	public static void initAsset() {

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
		
	    Entity.model = new Model(vertices, texture, indices);
	}
	
	public static void deleteAsset() {
		Entity.model = null;
	}
	
	public void collideWithEntity(Entity entity) {		
		Collision collision = bounding_box.getCollision(entity.bounding_box);
		
		if(collision.isIntersecting) {
				 collision.distance.x /= 2;
				 collision.distance.y /= 2;
			
				 bounding_box.correctPosition(entity.bounding_box, collision);
				 transform.pos.set(bounding_box.getCenter().x, bounding_box.getCenter().y, 0);
		
				 entity.bounding_box.correctPosition(bounding_box, collision);
				 entity.transform.pos.set(entity.bounding_box.getCenter().x, entity.bounding_box.getCenter().y, 0);
		}
			
	}
}
