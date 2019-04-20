package com.test.noticias

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.test.noticias.adapters.ArticleAdapter
import com.test.noticias.models.Article
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity(), ArticleAdapter.OnButtonClickListener {


    val TAG = "HomeActivity.kt"
    var articleAdapter: ArticleAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        peticionNoticia()

        articleAdapter = ArticleAdapter(listaArticulos, this)
       //-- my_recycler.setHasFixedSize(true)
        my_recycler.layoutManager = LinearLayoutManager(baseContext)
        my_recycler.adapter =  articleAdapter
    }

    override fun OnButtonVerNoticiaClic(article: Article, position: Int) {
      toast("Click en Noticia")
        val intent = Intent(this@MainActivity, WebViewActivity::class.java)
        intent.putExtra("article", position)
        startActivity(intent)
    }

    override fun OnButtoncompartirClic(article: Article, position: Int) {
        toast("Click en Compartir")

        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, article.url)

        startActivity(intent)


    }

    fun peticionNoticia(){
        val coladepeticiones = Volley.newRequestQueue(this)

        val peticionJSON = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener<JSONObject> {
                response ->
                try {
                       //--> Log.d(TAG, response.toString())

                    listaArticulos.removeAll(listaArticulos)

                    listaArticulos.addAll(procesarJSON(response))

                    articleAdapter!!.notifyDataSetChanged()

                    for (i in listaArticulos){
                        Log.d(TAG, i.toString())
                    }

                }catch (e: JSONException){
                        e.printStackTrace()
                }
            },
            Response.ErrorListener { error -> Log.d(TAG, error.toString()) })

        coladepeticiones.add(peticionJSON)
    }

    fun procesarJSON(json: JSONObject):ArrayList<Article> {
        val articles:JSONArray =  json.getJSONArray("articles")
        val lisArticles = ArrayList<Article>()

        for ( i in articles){
            val author =  i.getString("author")
            val title =  i.getString("title")
            val description =  i.getString("description")
            val url =  i.getString("url")
            val urlToImage =  i.getString("urlToImage")

            lisArticles.add(Article(author, title, description, url, urlToImage))
        }
        return lisArticles
    }
}
