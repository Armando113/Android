package com.blackdroidstudios.finkelme;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Armando on 7/5/2015.
 */
public class FinkelAdapter extends RecyclerView.Adapter<FinkelAdapter.FinkelViewHolder>
{
    //parameters
    protected List<FinkelCard> cardList;
    protected FinkelCard currentCard;
    protected TextView cardSeparator;
    protected MainActivity ma;

    //Tags
    public static final String MYCARDS = "MyCards";
    public static final String MYCOLLECTION = "MyCollection";
    public static final String MARKETPLACE = "Marketplace";

    //Constructor
    public FinkelAdapter(List<FinkelCard> newCardList, MainActivity newMA)
    {
        this.cardList = newCardList;
        this.ma = newMA;
        cardSeparator = (TextView)ma.getResources().getLayout(R.layout.recyclerview_separator);
    }

    //Get the type of layout of the Card
    @Override
    public int getItemViewType(int position)
    {
        return cardList.get(position).layoutType;
    }

    //Custom ViewHolder
    public class FinkelViewHolder extends RecyclerView.ViewHolder
    {
        protected TextView fullNameTV;
        protected TextView emailTV;
        protected TextView addressTV;
        protected TextView phoneTV;
        protected TextView professionTV;
        protected TextView companyTV;
        protected TextView websiteTV;
        protected ImageView logoIV;

        public FinkelViewHolder(View view)
        {
            super(view);
            fullNameTV = (TextView)view.findViewById(R.id.finkelcard_fullNameTV);
            emailTV = (TextView)view.findViewById(R.id.finkelcard_emailTV);
            addressTV = (TextView)view.findViewById(R.id.finkelcard_addressTV);
            phoneTV = (TextView)view.findViewById(R.id.finkelcard_phoneTV);
            professionTV = (TextView)view.findViewById(R.id.finkelcard_professionTV);
            companyTV = (TextView)view.findViewById(R.id.finkelcard_companyTV);
            websiteTV = (TextView)view.findViewById(R.id.finkelcard_websiteTV);
            logoIV = (ImageView)view.findViewById(R.id.finkelcard_logoIV);
        }
    }

    //Setup your custom ViewHolder
    @Override
    public FinkelAdapter.FinkelViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        //Check what layout the user has set up with their account
        View itemView = null;
        switch (viewType)
        {
            case R.layout.finkelcard_defaultlayout:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.finkelcard_defaultlayout, parent, false);
                break;

            default:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.finkelcard_defaultlayout, parent, false);
        }
        return new FinkelViewHolder(itemView);
    }

    //Bind the Info on the card
    @Override
    public void onBindViewHolder(FinkelViewHolder holder, int position)
    {
        FinkelCard finkelCard = cardList.get(position);

        holder.fullNameTV.setText(finkelCard.getFullName());
        holder.emailTV.setText(finkelCard.getEmailAddress());
        holder.addressTV.setText(finkelCard.getAddress());
        holder.phoneTV.setText(finkelCard.getPhoneNumber());
        holder.professionTV.setText(finkelCard.getProfession());
        holder.companyTV.setText(finkelCard.getCompany());
        holder.websiteTV.setText(finkelCard.getWebsite());

    }

    @Override
    public int getItemCount()
    {
        return 0;
    }
}
