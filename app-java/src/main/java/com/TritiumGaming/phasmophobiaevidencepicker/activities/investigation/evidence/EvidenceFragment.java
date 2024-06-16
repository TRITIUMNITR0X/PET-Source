package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.InvestigationFragment;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.investigation.section.lists.EvidenceListView;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.investigation.section.lists.GhostListView;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.investigation.section.InvestigationSection;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.investigation.sanity.SanityToolsLayout;

public class EvidenceFragment extends InvestigationFragment {

    protected InvestigationSection ghostSection, evidenceSection;

    protected GhostListView ghostList;
    protected EvidenceListView evidenceList;

    protected AppCompatImageView toggleCollapseButton;
    protected ConstraintLayout sanityTrackingConstraintLayout;

    protected SanityToolsLayout sanityToolsLayout;

    public EvidenceFragment(int layout) {
        super(layout);
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_evidence, container, false);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        sanityToolsLayout = view.findViewById(R.id.layout_sanity_tool);
        sanityToolsLayout.init(evidenceViewModel);

        if(evidenceViewModel.getSanityModel() != null) {
            evidenceViewModel.getSanityModel()
                    .setFlashTimeoutMax(globalPreferencesViewModel.getHuntWarningFlashTimeout());
        }

        // GHOST / EVIDENCE CONTAINERS
        FrameLayout
                column_left = view.findViewById(R.id.column_left),
                column_right = view.findViewById(R.id.column_right);
        column_right.findViewById(R.id.scrollview)
                .setVerticalScrollbarPosition(View.SCROLLBAR_POSITION_RIGHT);

        if(!globalPreferencesViewModel.isLeftHandSupportEnabled()) {
            ghostSection = (InvestigationSection) column_left.getChildAt(0);
            evidenceSection = (InvestigationSection) column_right.getChildAt(0);
        } else {
            evidenceSection = (InvestigationSection) column_left.getChildAt(0);
            ghostSection = (InvestigationSection) column_right.getChildAt(0);
        }

        ghostSection.setLabel(getString(R.string.investigation_section_title_ghosts));
        evidenceSection.setLabel(getString(R.string.investigation_section_title_evidence));

        ScrollView ghost_scrollview = ghostSection.findViewById(R.id.scrollview);
        ScrollView evidence_scrollview = evidenceSection.findViewById(R.id.scrollview);

        ghostList = new GhostListView(requireContext());
        evidenceList = new EvidenceListView(requireContext());

        ghostList.init(
                globalPreferencesViewModel, evidenceViewModel,
                popupWindow, ghostSection.findViewById(R.id.progressbar),
                adRequest);
        evidenceList.init(
                globalPreferencesViewModel, evidenceViewModel,
                popupWindow, evidenceSection.findViewById(R.id.progressbar),
                adRequest, ghostList);

        ViewStub list_ghosts = ghostSection.findViewById(R.id.list);
        ViewStub list_evidence = evidenceSection.findViewById(R.id.list);

        ghost_scrollview.removeView(list_ghosts);
        ghost_scrollview.addView(ghostList);

        evidence_scrollview.removeView(list_evidence);
        evidence_scrollview.addView(evidenceList);

        toggleCollapseButton = view.findViewById(R.id.button_toggleSanity);

        // SANITY COLLAPSIBLE
        sanityTrackingConstraintLayout = view.findViewById(R.id.constraintLayout_sanityTracking);

        if(toggleCollapseButton != null) {
            toggleCollapseButton.setOnClickListener(v -> {
                if(evidenceViewModel.isDrawerCollapsed()) {
                    sanityTrackingConstraintLayout.animate()
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationStart(Animator animation) {
                                    super.onAnimationStart(animation);

                                    sanityTrackingConstraintLayout.setVisibility(View.GONE);
                                }

                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);

                                    sanityTrackingConstraintLayout.setVisibility(View.VISIBLE);
                                    toggleCollapseButton.setImageLevel(2);
                                    evidenceViewModel.setDrawerCollapsed(false);
                                }
                            })
                            .start();
                } else {
                    sanityTrackingConstraintLayout.animate()
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationStart(Animator animation) {
                                    super.onAnimationStart(animation);

                                    sanityTrackingConstraintLayout.setVisibility(View.VISIBLE);
                                }
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationStart(animation);

                                    sanityTrackingConstraintLayout.setVisibility(View.GONE);
                                    toggleCollapseButton.setImageLevel(1);
                                    evidenceViewModel.setDrawerCollapsed(true);
                                }
                            })
                            .start();
                }
            });

            initCollapsible();
        }

        popupWindow = new PopupWindow(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);

        ghostList.createPopupWindow(popupWindow);
        evidenceList.createPopupWindow(popupWindow);
        new Thread(() -> ghostList.createViews()).start();
        new Thread(() -> evidenceList.createViews()).start();

    }

    private void initCollapsible() {
        if(evidenceViewModel.isDrawerCollapsed()) {
            sanityTrackingConstraintLayout.setVisibility(View.GONE);
            toggleCollapseButton.setImageLevel(1);
        } else {
            sanityTrackingConstraintLayout.setVisibility(View.VISIBLE);
            toggleCollapseButton.setImageLevel(2);
        }
    }

    @Override
    public void reset() {
        if (evidenceViewModel != null) {
            evidenceViewModel.reset();
        }

        // TODO Force progress bar update

        // TODO Reset and Pause PhaseTimer
    }

    public void requestInvalidateComponents() {

        if(evidenceViewModel != null) {
            evidenceViewModel.getGhostOrderData().updateOrder();
        }

        if(ghostList != null) {
            ghostList.forceResetGhostContainer();
        }

        // TODO Force progress bar update (aka reset)


        // TODO Reset Play/Pause button (to 'Play' state)\
    }

    @Override
    public void onDestroyView() {

        if(popupWindow != null) {
            popupWindow.dismiss();
            popupWindow = null;
        }

        super.onDestroyView();
    }

    @Override
    public void onResume() {

        super.onResume();
    }

    @Override
    protected void saveStates() {

    }

}
