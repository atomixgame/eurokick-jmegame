/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.games.football.state;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.post.SceneProcessor;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.Renderer;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.texture.FrameBuffer;
import com.jme3.util.BufferUtils;
import java.nio.ByteBuffer;
import java.util.List;

/**
 *
 * @author CuongNguyen
 */
public class ScreenCaptureState extends AbstractAppState implements SceneProcessor {

    private boolean captureLastFrame;
    private boolean capture = false;
    private Renderer renderer;
    private RenderManager rm;
    private ByteBuffer outBuf;
    private int shotIndex = 0;
    private int width, height;
    private boolean enableCapture = false;
    private Application app;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);

        this.app = app;
    }

    public void setupScreenCapture() {
        List<ViewPort> vps = app.getRenderManager().getPostViews();
        ViewPort last = vps.get(vps.size() - 1);
        last.addProcessor(this);
    }

    public void takeScreenshot() {
        capture = true;
    }

    public void initialize(RenderManager rm, ViewPort vp) {
        if (enableCapture) {
            renderer = rm.getRenderer();
            this.rm = rm;
            reshape(vp, vp.getCamera().getWidth(), vp.getCamera().getHeight());
        }
    }

    public void reshape(ViewPort vp, int w, int h) {
        if (enableCapture) {
            outBuf = BufferUtils.createByteBuffer(w * h * 4);
            width = w;
            height = h;
        }
    }

    public void preFrame(float tpf) {
    }

    public void postQueue(RenderQueue rq) {
    }

    public void postFrame(FrameBuffer out) {

        if (enableCapture && capture) {
            capture = false;
            shotIndex++;

            Camera curCamera = rm.getCurrentCamera();
            int viewX = (int) (curCamera.getViewPortLeft() * curCamera.getWidth());
            int viewY = (int) (curCamera.getViewPortBottom() * curCamera.getHeight());
            int viewWidth = (int) ((curCamera.getViewPortRight() - curCamera.getViewPortLeft()) * curCamera.getWidth());
            int viewHeight = (int) ((curCamera.getViewPortTop() - curCamera.getViewPortBottom()) * curCamera.getHeight());

            renderer.setViewPort(0, 0, width, height);
            renderer.readFrameBuffer(out, outBuf);
            renderer.setViewPort(viewX, viewY, viewWidth, viewHeight);

            captureLastFrame = true;
        }
    }
}
