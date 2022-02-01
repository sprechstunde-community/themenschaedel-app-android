package sprechstunde.community.themenschaedel.listener;

import java.util.List;

import sprechstunde.community.themenschaedel.model.Episode;

public interface ParentChildFragmentListener {

    enum SORTED_BY {
        DATE_UP,
        DATE_DOWN,
        TITLE_UP,
        TITLE_DOWN,
        NUMBER_UP,
        NUMBER_DOWN,
        USER,
        STATE
    }

    String EXT_RULES = "< ' ' < '.'"+
            "<0<1<2<3<4<5<6<7<8<9<a,A<b,B<c,C<d,D<ð,Ð<e,E<f,F<g,G<h,H<i,I<j"+
            ",J<k,K<l,L<m,M<n,N<o,O<p,P<q,Q<r,R<s, S & SS,ß<t,T& TH, Þ &TH,"+
            "þ <u,U<v,V<w,W<x,X<y,Y<z,Z&AE,Æ&AE,æ&OE,Œ&OE,œ";

    boolean onScrollBackToTop();
    void onSortChanged(SORTED_BY sortedBy);
    void onSearch(List<Episode> episodeList);
}
