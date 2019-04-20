package com.test.noticias.adapters


import android.support.v7.view.menu.ActionMenuItemView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.SurfaceHolder
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import com.test.noticias.R
import com.test.noticias.models.Article
import kotlinx.android.synthetic.main.layout_news_items.view.*
import java.text.FieldPosition

class ArticleAdapter(
    val list: ArrayList<Article>,
    val buttonClic: OnButtonClickListener

):RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder =
        ArticleViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_news_items, parent, false))

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(list[position], buttonClic)
    }

    override fun getItemCount(): Int = list.size

    class ArticleViewHolder (articleItemView: View):RecyclerView.ViewHolder(articleItemView){
        fun bind(article:Article, onButtonClickListener: OnButtonClickListener) = with(itemView){
            TextViewTitle.text = article.title
            TextViewDescription.text = article.description
            Picasso.with(context).load(article.urlToImage).into(ImageViewArticle)

            btnCompartir.setOnClickListener { onButtonClickListener.OnButtoncompartirClic(article, position)  }
            btnVerNoticia.setOnClickListener { onButtonClickListener.OnButtonVerNoticiaClic(article, position) }
        }
    }

    interface OnButtonClickListener{
        fun OnButtonVerNoticiaClic(article: Article, position: Int)
        fun OnButtoncompartirClic(article: Article, position: Int)
    }
}