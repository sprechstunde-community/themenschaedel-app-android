package sprechstunde.community.themenschaedel.listener;

public interface ParentChildFragmentListener {

    enum SORTED_BY {
        DATE,
        TITLE,
        USER,
        STATE
    }

    String EXT_RULES = "< ' ' < '.'"+
            "<0<1<2<3<4<5<6<7<8<9<a,A<b,B<c,C<d,D<ð,Ð<e,E<f,F<g,G<h,H<i,I<j"+
            ",J<k,K<l,L<m,M<n,N<o,O<p,P<q,Q<r,R<s, S & SS,ß<t,T& TH, Þ &TH,"+
            "þ <u,U<v,V<w,W<x,X<y,Y<z,Z&AE,Æ&AE,æ&OE,Œ&OE,œ";

    void onSortChanged(SORTED_BY sortedBy);
}
