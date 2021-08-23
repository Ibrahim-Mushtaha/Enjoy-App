<h1 align="center"><a id="user-content-moviehunt" class="anchor" aria-hidden="true" href="#moviehunt"><svg class="octicon octicon-link" viewBox="0 0 16 16" version="1.1" width="16" height="16" aria-hidden="true"><path fill-rule="evenodd" d="M7.775 3.275a.75.75 0 001.06 1.06l1.25-1.25a2 2 0 112.83 2.83l-2.5 2.5a2 2 0 01-2.83 0 .75.75 0 00-1.06 1.06 3.5 3.5 0 004.95 0l2.5-2.5a3.5 3.5 0 00-4.95-4.95l-1.25 1.25zm-4.69 9.64a2 2 0 010-2.83l2.5-2.5a2 2 0 012.83 0 .75.75 0 001.06-1.06 3.5 3.5 0 00-4.95 0l-2.5 2.5a3.5 3.5 0 004.95 4.95l1.25-1.25a.75.75 0 00-1.06-1.06l-1.25 1.25a2 2 0 01-2.83 0z"></path></svg></a>Movie App</h1>

<p align="center">
Movie App is a sample Android project using <a href="https://www.themoviedb.org/" rel="nofollow">The Movie DB</a> API based on MVVM architecture. It showcases the app development with well-designed architecture and up-to-date Android tech stacks.
</p>

<h2><a id="user-content--features" class="anchor" aria-hidden="true" href="#-features"><svg class="octicon octicon-link" viewBox="0 0 16 16" version="1.1" width="16" height="16" aria-hidden="true"><path fill-rule="evenodd" d="M7.775 3.275a.75.75 0 001.06 1.06l1.25-1.25a2 2 0 112.83 2.83l-2.5 2.5a2 2 0 01-2.83 0 .75.75 0 00-1.06 1.06 3.5 3.5 0 004.95 0l2.5-2.5a3.5 3.5 0 00-4.95-4.95l-1.25 1.25zm-4.69 9.64a2 2 0 010-2.83l2.5-2.5a2 2 0 012.83 0 .75.75 0 001.06-1.06 3.5 3.5 0 00-4.95 0l-2.5 2.5a3.5 3.5 0 004.95 4.95l1.25-1.25a.75.75 0 00-1.06-1.06l-1.25 1.25a2 2 0 01-2.83 0z"></path></svg></a><a id="user-content--features" href="#-features"></a><g-emoji class="g-emoji" alias="sparkles" fallback-src="https://github.githubassets.com/images/icons/emoji/unicode/2728.png">✨</g-emoji> Features:</h2>
<ul>
<li>100% Kotlin</li>
<li>MVVM architecture</li>
<li>Android architecture components and Jetpack</li>
<li>Single activity</li>
<li>Rxjava</li>
</ul>
<br>

<div align="center">
   <table align="center" border="0">
  <tbody><tr>
      <td><a target="_blank" rel="noopener noreferrer" href="https://github.com/Ibrahim-Mushtaha/MovieHunt/blob/master/app/src/main/res/drawable/ic_image1.jpg"><img width="360" src="https://github.com/Ibrahim-Mushtaha/MovieHunt/blob/master/app/src/main/res/drawable/ic_image1.jpg" style="max-width:20%;"></a>
    </td>
    <td>
<a target="_blank" rel="noopener noreferrer" href="https://github.com/Ibrahim-Mushtaha/MovieHunt/blob/master/app/src/main/res/drawable/ic_image2.jpg"><img width="360" src="https://github.com/Ibrahim-Mushtaha/MovieHunt/blob/master/app/src/main/res/drawable/ic_image2.jpg" style="max-width:20%;"></a>
  </td>
    <td>
<a target="_blank" rel="noopener noreferrer" href="https://github.com/Ibrahim-Mushtaha/MovieHunt/blob/master/app/src/main/res/drawable/ic_image3.jpg"><img width="360" src="https://github.com/Ibrahim-Mushtaha/MovieHunt/blob/master/app/src/main/res/drawable/ic_image3.jpg" style="max-width:20%;"></a>
  </td>
  </tr>
  </tbody></table>
</div>

<h2><a id="user-content-api-key-" class="anchor" aria-hidden="true" href="#api-key-"><svg class="octicon octicon-link" viewBox="0 0 16 16" version="1.1" width="16" height="16" aria-hidden="true"><path fill-rule="evenodd" d="M7.775 3.275a.75.75 0 001.06 1.06l1.25-1.25a2 2 0 112.83 2.83l-2.5 2.5a2 2 0 01-2.83 0 .75.75 0 00-1.06 1.06 3.5 3.5 0 004.95 0l2.5-2.5a3.5 3.5 0 00-4.95-4.95l-1.25 1.25zm-4.69 9.64a2 2 0 010-2.83l2.5-2.5a2 2 0 012.83 0 .75.75 0 001.06-1.06 3.5 3.5 0 00-4.95 0l-2.5 2.5a3.5 3.5 0 004.95 4.95l1.25-1.25a.75.75 0 00-1.06-1.06l-1.25 1.25a2 2 0 01-2.83 0z"></path></svg></a>API Key <g-emoji class="g-emoji" alias="key" fallback-src="https://github.githubassets.com/images/icons/emoji/unicode/1f511.png">🔑</g-emoji></h2>
<p>You will need to provide developer key to fetch the data from TMDB API.</p>
<ul>
<li>Generate a new key (v3 auth) from <a href="https://www.themoviedb.org/settings/api" rel="nofollow">here</a>. Copy the key and go back to Android project.</li>
<li>Create a new kotlin file <code>ApiKey.kt</code> in path <code>./buildSrc/src/main/kotlin/</code>.</li>
<li>Define a constant <code>API_KEY</code> with the double quotes, it looks like</li>
<li>Add Rxjava Dependency</li><br>

<pre>
<p><span>dependencies</span> {
implementation <span><span>'</span>io.reactivex.rxjava3:rxandroid:3.0.0'</span></span>
implementation <span><span>'</span>io.reactivex.rxjava3:rxjava:3.0.0'</span></span>
implementation <span><span>'</span>com.github.akarnokd:rxjava3-retrofit-adapter:3.0.0'</span></span>
}</p></pre>
</ul>
