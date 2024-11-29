public class Bird {
    private float x, y;
    private float velocity;

    public Bird(float x, float y) {
        this.x = x;
        this.y = y;
        this.velocity = 0;
    }

    public void update() {
        velocity += 0.5f; // Gravity
        y += velocity;

        if (y + 20 > 600) { // Window height
            y = 600 - 20;
            velocity = 0;
        } else if (y < 0) {
            y = 0;
            velocity = 0;
        }
    }

    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
