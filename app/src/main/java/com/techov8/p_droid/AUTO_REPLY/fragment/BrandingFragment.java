package com.techov8.p_droid.AUTO_REPLY.fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.techov8.p_droid.R;

import java.util.List;

import static android.content.Intent.ACTION_VIEW;
import static android.content.Intent.CATEGORY_BROWSABLE;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class BrandingFragment extends Fragment {
    private ImageButton githubBtn;
    private ImageButton share_layout;
    private Button watomaticSubredditBtn, whatsNewBtn;
    private List<String> whatsNewUrls;
    private int gitHubReleaseNotesId = -1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_branding, container, false);

        githubBtn = view.findViewById(R.id.watomaticGithubBtn);
        share_layout = view.findViewById(R.id.share_btn);
        watomaticSubredditBtn = view.findViewById(R.id.watomaticSubredditBtn);
        whatsNewBtn = view.findViewById(R.id.whatsNewBtn);
        whatsNewBtn.setOnClickListener(v -> {
            //launchApp();
        });

        share_layout.setOnClickListener(v -> launchShareIntent());

        watomaticSubredditBtn.setOnClickListener(v -> {
            /*
            String url = getString(R.string.watomatic_subreddit_url);
            startActivity(
                    new Intent(ACTION_VIEW).setData(Uri.parse(url))
            );

             */
        });

        githubBtn.setOnClickListener(v -> {
            /*
            String url = getString(R.string.watomatic_github_url);
            startActivity(
                    new Intent(ACTION_VIEW).setData(Uri.parse(url))
            );

             */
        });

        //getGthubReleaseNotes();

        return view;
    }
/*
    private void launchApp() {

            launchAppLegacy();
            return;

       // boolean isLaunched = false;
       for (String eachReleaseUrl: whatsNewUrls) {
           // if (isLaunched) { break;}
            try {
                // In order for this intent to be invoked, the system must directly launch a non-browser app.
                // Ref: https://developer.android.com/training/package-visibility/use-cases#avoid-a-disambiguation-dialog
                Intent intent = new Intent(ACTION_VIEW, Uri.parse(eachReleaseUrl))
                        .addCategory(CATEGORY_BROWSABLE)
                        .setFlags(FLAG_ACTIVITY_NEW_TASK );
                startactivity(intent);
               // isLaunched = true;
            } catch (ActivityNotFoundException e) {
                // This code executes in one of the following cases:
                // 1. Only browser apps can handle the intent.
                // 2. The user has set a browser app as the default app.
                // 3. The user hasn't set any app as the default for handling this URL.
               // isLaunched = false;
            }
        }
        //if (!isLaunched) { // Open Github latest release url in browser if everything else fails
            String url = getString(R.string.watomatic_github_latest_release_url);
            startactivity(new Intent(ACTION_VIEW).setData(Uri.parse(url)));
        }
    }

    private void launchAppLegacy() {
        boolean isLaunched = false;
        for(String url: whatsNewUrls){
            Intent intent = new Intent(ACTION_VIEW, Uri.parse(url));
            List<ResolveInfo> list = getActivity().getPackageManager()
                    .queryIntentActivities(intent, 0);
            List<ResolveInfo> possibleBrowserIntents = getActivity().getPackageManager()
                    .queryIntentActivities(new Intent(ACTION_VIEW, Uri.parse("http://www.deekshith.in/")), 0);
            Set<String> excludeIntents = new HashSet<>();
            for (ResolveInfo eachPossibleBrowserIntent: possibleBrowserIntents) {
                excludeIntents.add(eachPossibleBrowserIntent.activityInfo.name);
            }
            //Check for non browser application
            for(ResolveInfo resolveInfo: list) {
                if (!excludeIntents.contains(resolveInfo.activityInfo.name)) {
                    intent.setPackage(resolveInfo.activityInfo.packageName);
                    startactivity(intent);
                    isLaunched = true;
                    break;
                }
            }
            if(isLaunched){
                break;
            }
        }
        if (!isLaunched) { // Open Github latest release url in browser if everything else fails
            String url = getString(R.string.watomatic_github_latest_release_url);
            startactivity(new Intent(ACTION_VIEW).setData(Uri.parse(url)));
        }
    }

    private void startactivity(Intent intent){
        PreferencesManager.getPreferencesInstance(getActivity()).setGithubReleaseNotesId(gitHubReleaseNotesId);
        startActivity(intent);
        showHideWhatsNewBtn(false);
    }

    private void getGthubReleaseNotes() {
        GetReleaseNotesService releaseNotesService = RetrofitInstance.getRetrofitInstance().create(GetReleaseNotesService.class);
        Call<List<GithubReleaseNotes>> call = releaseNotesService.getReleaseNotes();
        call.enqueue(new Callback<List<GithubReleaseNotes>>() {
            @Override
            public void onResponse(Call<List<GithubReleaseNotes>> call, Response<List<GithubReleaseNotes>> response) {
                if (response.body() != null) {
                    parseReleaseNotesResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<GithubReleaseNotes>> call, Throwable t) {

            }
        });
    }

    private void parseReleaseNotesResponse(List<GithubReleaseNotes> releaseNotesList) {
        for (GithubReleaseNotes releaseNotes: releaseNotesList
             ) {
            String appVersion = "v" + BuildConfig.VERSION_NAME;
            //in the list of release notes, check the release notes for this version of app
            if(releaseNotes.getTagName().equalsIgnoreCase(appVersion)) {
                gitHubReleaseNotesId = releaseNotes.getId();
                String body = releaseNotes.getBody();
                int gitHubId = PreferencesManager.getPreferencesInstance(getActivity()).getGithubReleaseNotesId();
                if ((gitHubId == 0 || gitHubId != gitHubReleaseNotesId) && !body.contains("minor-release: true")) {
                    //Split the body into separate lines and search for line starting with "view release notes on"
                    String[] splitStr = body.split("\n");
                    if (splitStr.length > 0) {
                        for (String s : splitStr) {
                            if (s.toLowerCase().startsWith("view release notes on")) {
                                whatsNewUrls = extractLinks(s);
                                showHideWhatsNewBtn(true);
                                break;
                            }
                        }
                    }
                    break;
                }
            }
        }
    }

    private void showHideWhatsNewBtn(boolean show){
        watomaticSubredditBtn.setVisibility(show ? View.GONE : View.VISIBLE);
        whatsNewBtn.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    public static List<String> extractLinks(String text) {
        List<String> links = new ArrayList<String>();
        Matcher m = Patterns.WEB_URL.matcher(text);
        while (m.find()) {
            String url = m.group();
            links.add(url);
        }

        return links;
    }

 */


    private void launchShareIntent() {
        /*
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getResources().getString(R.string.share_subject));
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, getResources().getString(R.string.share_app_text));
        startActivity(Intent.createChooser(sharingIntent, "Share app via"));

         */
    }
}
