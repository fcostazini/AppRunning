package studios.thinkup.com.apprunning.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ViewFlipper;

import studios.thinkup.com.apprunning.R;
import studios.thinkup.com.apprunning.TutorialActivity;
import studios.thinkup.com.apprunning.model.TutorialesPaginaEnum;

/**
 * Created by fcostazini on 06/08/2015.
 * Manejo de tutorial
 */
public class TutorialFragment extends Fragment implements View.OnTouchListener {

    private ViewFlipper viewFlipper;
    private float lastX;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View rootView = inflater.inflate(R.layout.tutorial_fragment, container, false);
        viewFlipper = (ViewFlipper) rootView.findViewById(R.id.viewFlipper);
        final Button next = (Button) rootView.findViewById(R.id.btn_next);
        final Button back = (Button) rootView.findViewById(R.id.btn_back);

        viewFlipper.setOnTouchListener(this);
        viewFlipper.setDisplayedChild(getFirstScreen());
        if (viewFlipper.getDisplayedChild() == 0) {
            back.setVisibility(View.GONE);
        } else {
            back.setVisibility(View.VISIBLE);
        }
        if (viewFlipper.getDisplayedChild() == viewFlipper.getChildCount()) {
            next.setVisibility(View.GONE);
        } else {
            next.setVisibility(View.VISIBLE);
        }
        next.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                if (viewFlipper.getDisplayedChild() < viewFlipper.getChildCount()) {
                    viewFlipper.setInAnimation(getActivity(), R.anim.in_from_right);
                    viewFlipper.setOutAnimation(getActivity(), R.anim.out_to_left);
                    viewFlipper.showNext();
                }
                actualizarVisibilidad();
            }

        });

        back.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {


                if (viewFlipper.getDisplayedChild() > 0) {
                    viewFlipper.setInAnimation(getActivity(), R.anim.in_from_left);
                    viewFlipper.setOutAnimation(getActivity(), R.anim.out_to_right);
                    viewFlipper.showPrevious();
                }
                actualizarVisibilidad();


            }
        });
        return rootView;
    }

    protected void actualizarVisibilidad() {
        if (this.getView() != null) {
            ViewFlipper viewFlipper = (ViewFlipper) this.getView().findViewById(R.id.viewFlipper);
            Button next = (Button) this.getView().findViewById(R.id.btn_next);
            Button back = (Button) this.getView().findViewById(R.id.btn_back);
            if (viewFlipper.getDisplayedChild() == 0) {
                next.setVisibility(View.VISIBLE);
                back.setVisibility(View.GONE);
            } else if (viewFlipper.getDisplayedChild() == viewFlipper.getChildCount() - 1) {
                next.setVisibility(View.GONE);
                back.setVisibility(View.VISIBLE);
            } else {
                next.setVisibility(View.VISIBLE);
                back.setVisibility(View.VISIBLE);
            }

        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent touchevent) {
        switch (touchevent.getAction()) {
            // when user first touches the screen to swap
            case MotionEvent.ACTION_DOWN: {
                lastX = touchevent.getX();
                break;
            }
            case MotionEvent.ACTION_UP: {
                float currentX = touchevent.getX();

                // if left to right swipe on screen
                if (lastX < currentX) {
                    // If no more View/Child to flip
                    if (viewFlipper.getDisplayedChild() == 0)
                        return false;

                    // set the required Animation type to ViewFlipper
                    // The Next screen will come in form Left and current Screen will go OUT from Right
                    viewFlipper.setInAnimation(getActivity(), R.anim.in_from_left);
                    viewFlipper.setOutAnimation(getActivity(), R.anim.out_to_right);
                    // Show the next Screen
                    viewFlipper.showPrevious();
                }

                // if right to left swipe on screen
                if (lastX > currentX) {
                    if (viewFlipper.getDisplayedChild() == viewFlipper.getChildCount() - 1)
                        return false;
                    // set the required Animation type to ViewFlipper
                    // The Next screen will come in form Right and current Screen will go OUT from Left
                    viewFlipper.setInAnimation(getActivity(), R.anim.in_from_right);
                    viewFlipper.setOutAnimation(getActivity(), R.anim.out_to_left);
                    // Show The Previous Screen
                    viewFlipper.showNext();
                }
                break;
            }
        }
        actualizarVisibilidad();
        return true;
    }

    public int getFirstScreen() {
        int firstScreen = 0;
        if (this.getActivity().getIntent().getExtras() != null &&
                this.getActivity().getIntent().getExtras().containsKey(TutorialActivity.PAGINA_TUTORIAL)) {
            if (this.getActivity().getIntent().getExtras().getInt(TutorialActivity.PAGINA_TUTORIAL) > 0) {

                return TutorialesPaginaEnum.getById(this.getActivity().getIntent().getExtras().getInt(TutorialActivity.PAGINA_TUTORIAL)).getId();
            } else {
                return firstScreen;
            }

        }
        return firstScreen;
    }
}
