package com.iizuhn.rrlxqrs;

public class MaterialMovable extends Entity {
    public MaterialMovable(int x, int y, int imgRsc, Scene scene) {
        super(x, y, imgRsc, scene);
        setType(Type.MATERIAL_MOVABLE);
    }
}
