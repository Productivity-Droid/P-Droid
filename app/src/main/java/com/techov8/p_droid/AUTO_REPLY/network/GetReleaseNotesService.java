package com.techov8.p_droid.AUTO_REPLY.network;

import com.techov8.p_droid.AUTO_REPLY.model.GithubReleaseNotes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetReleaseNotesService {
    @GET("/repos/adeekshith/watomatic/releases")
    Call<List<GithubReleaseNotes>> getReleaseNotes();
}
