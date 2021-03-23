package entity;

import essentials101.*;
import world.*;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ENTER;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import collision.AABB;
import collision.Collision;

public class Player extends Entity{
	public static final int ANIM_IDLE = 0;
	public static final int ANIM_SIZE = 2;
	public static final int ANIM_WALK = 1;
	
	public Player(Transform transform) {
		super(ANIM_SIZE, transform);
		
		setAnimation(ANIM_IDLE, new Animation(1, 2, "player/idle"));
		setAnimation(ANIM_WALK, new Animation(4, 60, "player/walking"));
	}

	@Override
	public void update(float delta, Window window, Camera camera, World world) {
		Vector2f movement = new Vector2f();
		
		if(window.getInput().isKeyDown(GLFW_KEY_UP))
			 movement.add(0, 10*delta);
		 
		 if(window.getInput().isKeyDown(GLFW_KEY_LEFT))	
			 movement.add(-10*delta, 0);
		 
		 if(window.getInput().isKeyDown(GLFW_KEY_DOWN))
			 movement.add(0, -10*delta);
		 
		 if(window.getInput().isKeyDown(GLFW_KEY_RIGHT))
			 movement.add(10*delta, 0);	
		 
		 this.move(movement);
		 
		 if(movement.x!=0 || movement.y!=0)
			 useAnimation(ANIM_WALK);
		 else
			 useAnimation(ANIM_IDLE);
		 camera.getPosition().lerp(transform.pos.mul(-world.getScale(), new Vector3f()), 0.05f);
		 //camera.setPosition(transform.pos.mul(-world.getScale(), new Vector3f()));
	}
}
