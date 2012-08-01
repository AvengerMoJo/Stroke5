/*
 *  Copyright (C) 2011 AvengerGear Inc
 *
 *  This program is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU General Public License
 *  as published by the Free Software Foundation; either version 2
 *  of the License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 */

package com.avengergear.android.stroke5;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;

import android.inputmethodservice.InputMethodService;

import android.util.AttributeSet;
import android.util.Log;

import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.ScrollView;

import android.view.inputmethod.InputConnection;

/**
 * The children of a TableLayout cannot specify the layout_width attribute.
 * Width is always MATCH_PARENT. However, the layout_height attribute can be
 * defined by a child; default value is WRAP_CONTENT. If the child is a 
 * TableRow, then the height is always WRAP_CONTENT.
 *
 * So Row Height is set by the Button(CandidateView)
 **/

public class CandidateViewContainer extends TableLayout {

	private Context	mContext;
	private Stroke5	mStroke5; 
	CandidateView mCandidateView[]; 

	public CandidateViewContainer(Context context) {
		super(context);
		Log.d("Stroke5IME", "CandidateViewContainer->init Context");
		init(context);
	}

	public CandidateViewContainer(Context context, AttributeSet attrs) {
		super(context, attrs);
		Log.d("Stroke5IME", "CandidateViewContainer->init Context AttributeSet");
		init(context);
	}

	void init(Context context){
		mContext=context;
		//TableLayout.LayoutParams trParams= new TableLayout.LayoutParams
		//	(TableLayout.LayoutParams.FILL_PARENT,TableLayout.LayoutParams.WRAP_CONTENT);
		//trParams.setMargins(0,0,0,0);
		//this.setLayoutParams(trParams);
	}

	public void setInput(Stroke5 s5){ 
		mStroke5 = s5; 
	}	

	/**
	 * Create all the candidates as Button, let calculate the actual size
	 * in the future. 
	 *
	 * ToDo: 
	 * make sure the candidates size is fit into the screen. 
	 **/

	public void createCandidatesButton(String candidates){
		if(candidates == null)
			candidates = "-";
		this.removeAllViews();
		//for( int row =0; row < candidates.length()/8; row++){
			TableRow tr = new TableRow(mContext);
			//TableLayout.LayoutParams trParams= new TableLayout.LayoutParams
				//(TableLayout.LayoutParams.FILL_PARENT,TableLayout.LayoutParams.WRAP_CONTENT);
			//trParams.setMargins(-4,-1,-4,-1);
			//tr.setLayoutParams(trParams);
			//tr.setPadding(-1,-1,-4,-1);
			//for( int index =0; index < 8 && index+(row*8) < candidates.length(); index++){
			for( int index =0; index < candidates.length(); index++){
				//int x = index+(row*8); 
				tr.addView( new CandidateView(mContext, candidates.substring( index, index+1 ), mStroke5));
			}
			this.addView( tr );
		//}
	//	for( int row =0; row < candidates.length()/4; row++){
	//		TableRow tr = new TableRow(mContext);
	//		for( int index=0; index < 4 && index+(row*4) < candidates.length(); index++ ){
	//			tr.addView( new CandidateView(mContext, candidates.substring( index+(row*4), index+(row*4)+1 )));
	//		Log.d("Stroke5IME", "initialize CandidateViewContainer Row="+row+ " Col="+index );
	//		}
	//		this.addView( tr );
	//	}
	}

	public void setupSingleCharTable(String lookupText){
		if( lookupText.equals("n") )
			createCandidatesButton( "乙也了出好如子能乀乁乚乛" );
		else if( lookupText.equals( "m" ))
			createCandidatesButton( "一不來到可大有要" );
		else if( lookupText.equals( "," ))
			createCandidatesButton( "的他個們年得我會" );
		else if( lookupText.equals( "." ))
			createCandidatesButton( "。〃〈〉《》「」『』【】〒〔〕〝〞︳︴︵︶︷︸︹︺︻︼︽︾︿﹀﹁﹂﹃﹄﹉﹊﹌﹍﹎﹏﹒﹙﹚﹛﹜﹝﹞﹢﹣﹤﹥﹦﹨！＄（）＋，－／：；＜＝＞？＠＼＿｛｜｝～￡￣￥");
			//createCandidatesButton( "§¯°±·×÷ˍΩαβγηθκλμνξπστχω–‘’“”‥…‧′‵※€℃℅℉ⅠⅡⅢⅣⅤⅥⅦⅧⅨⅩ←↑→↓↖↗↘↙∕√∞∟∠∥∩∪∫∮∴∵≒≠≡≦≧⊕⊙⊥⊿─│┌┐└┘├┤┬┴┼═║╒╓╔╕╖╗╘╙╚╛╜╝╞╟╠╡╢╣╤╥╦╧╨╩╪╫╬╭╮╯╰╱╲╳╴▁▂▃▄▅▆▇█▉▊▋▌▍▎▏▔▕■□▲△▼▽◆◇○◎●◢◣◤◥★☆♀♂、。〃〈〉《》「」『』【】〒〔〕〝〞〡〢〣〤〥〦〧〨〩㊣㎎㎏㎜㎝㎞㎡㏄㏎㏑㏒㏕丶兙兛兝兞兡兣嗧糎︱︳︴︵︶︷︸︹︺︻︼︽︾︿﹀﹁﹂﹃﹄﹉﹊﹌﹍﹎﹏﹒﹙﹚﹛﹜﹝﹞﹢﹣﹤﹥﹦﹨！＄（）＋，－／：；＜＝＞？＠＼＿｛｜｝～￡￣￥");
		else if( lookupText.equals( "/" ))
			createCandidatesButton( "上中國以對是時著過丨亅" );
	}
	
	public void setupTwoCharTable(char char1, char char2){
		Log.d("Stroke5IME", "CandidateViewContainer->setupTwoCharTable: lookupTest->"+char1 + char2);
		switch(char1){
			case ',': 
				if(char2 == ','){
					createCandidatesButton("反往待很律後從微德所般行術街質近須");
				} else if(char2 == '.'){
					createCandidatesButton("人今令入全八公分卻受合學愛爭覺金錢食乂");
				} else if(char2 == '/'){
					createCandidatesButton("代任但位何作你使便做像向學樂自與身進亻");
				} else if(char2 == 'm'){
					createCandidatesButton("利制動千和手每氣無物特生看知種等重香");
				} else if(char2 == 'n'){
					createCandidatesButton("乃九儿几勹匕及各名外多月然用色角解風⺆⺇⺈");
				}
				break;
			case '.':
				if(char2 == ','){
					createCandidatesButton("並前勞半導差料曾火為燈益精美義道類首丷");
				} else if(char2 == '.'){
					createCandidatesButton("安定家容實情決沒治法活流海深清港濟⺀");
				} else if(char2 == '/'){
				} else if(char2 == 'm'){
					createCandidatesButton("主就市度意應文新方於次裏說這部高麼亠冫");
				} else if(char2 == 'n'){
					createCandidatesButton("初啟心必永社神福禮被裡視軍運顧讠冖");
				}
				break;
			case '/':
				if(char2 == ','){
					createCandidatesButton("劣小少尖省雀");
				} else if(char2 == '.'){
					createCandidatesButton("光削卜嘗堂尚常掌敞棠當耀裳賞輝逍黨");
				} else if(char2 == '/'){
					createCandidatesButton("叢懟業鑿刂");
				} else if(char2 == 'm'){
					createCandidatesButton("劇北歡步歲若苦英華萬落藝處觀豐丄⺊");
				} else if(char2 == 'n'){
					createCandidatesButton("內只同員問四因日明最水由見還開間關點冂");
				}
				break;
			case 'm':
				if(char2 == ','){
					createCandidatesButton("原友否在壓太奇存布感成歷死百石確而面願丆厂");
				} else if(char2 == '.'){
					createCandidatesButton("匯平雪雲零雷電需震霉霍霖霜霞霧露霸靈");
				} else if(char2 == '/'){
					createCandidatesButton("丁下事五兩其十去地工想政本樣者起都長丅");
				} else if(char2 == 'm'){
					createCandidatesButton("二元天夫式形未玩現球理環示素表責青");
				} else if(char2 == 'n'){
					createCandidatesButton("七切勁夷妻屯比牙疑皆穎至致雅頓頸丂匚匸");
				}
				break;
			case 'n':
				if(char2 == ','){
					createCandidatesButton("刀力加努女她妙妹始姐婚婦媽忍架皮頗飛");
				} else if(char2 == '.'){
					createCandidatesButton("允勇務參又台態柔登發矛矣習翠通鄧預廴厶乄");
				} else if(char2 == '/'){
					createCandidatesButton("了壯孔孤孩孫將巴承收牆狀獎疏眉蛋裝違阝凵卩丩");
				} else if(char2 == 'm'){
					createCandidatesButton("刁即司問局展己已建張強改書民選那開間");
				} else if(char2 == 'n'){
					createCandidatesButton("乜幾樂約紅組結絕給統經線總變院除際響巜㔾");
				}
				break;
		}
	}
}
	/*
		if(lookupText.equals(",,"))
			createCandidatesButton("反往待很律後從微德所般行術街質近須");
		else if(lookupText.equals(",."))
			createCandidatesButton("人今令入全八公分卻受合學愛爭覺金錢食乂");
		else if(lookupText.equals(",/"))
			createCandidatesButton("代任但位何作你使便做像向學樂自與身進亻");
		else if(lookupText.equals(",m"))
			createCandidatesButton("利制動千和手每氣無物特生看知種等重香");
		else if(lookupText.equals(",n"))
			createCandidatesButton("乃九儿几勹匕及各名外多月然用色角解風⺆⺇⺈");
		else if(lookupText.equals(".,"))
			createCandidatesButton("並前勞半導差料曾火為燈益精美義道類首丷");
		else if(lookupText.equals(".."))
			createCandidatesButton("安定家容實情決沒治法活流海深清港濟⺀");
		else if(lookupText.equals(".m"))
			createCandidatesButton("主就市度意應文新方於次裏說這部高麼亠冫");
		else if(lookupText.equals(".n"))
			createCandidatesButton("初啟心必永社神福禮被裡視軍運顧讠冖");
		else if(lookupText.equals("/,"))
			createCandidatesButton("劣小少尖省雀");
		else if(lookupText.equals("/."))
			createCandidatesButton("光削卜嘗堂尚常掌敞棠當耀裳賞輝逍黨");
		else if(lookupText.equals("//"))
			createCandidatesButton("叢懟業鑿刂");
		else if(lookupText.equals("/m"))
			createCandidatesButton("劇北歡步歲若苦英華萬落藝處觀豐丄⺊");
		else if(lookupText.equals("/n"))
			createCandidatesButton("內只同員問四因日明最水由見還開間關點冂");
		else if(lookupText.equals("m,"))
			createCandidatesButton("原友否在壓太奇存布感成歷死百石確而面願丆厂");
		else if(lookupText.equals("m."))
			createCandidatesButton("匯平雪雲零雷電需震霉霍霖霜霞霧露霸靈");
		else if(lookupText.equals("m/"))
			createCandidatesButton("丁下事五兩其十去地工想政本樣者起都長丅");
		else if(lookupText.equals("mm"))
			createCandidatesButton("二元天夫式形未玩現球理環示素表責青");
		else if(lookupText.equals("mn"))
			createCandidatesButton("七切勁夷妻屯比牙疑皆穎至致雅頓頸丂匚匸");
		else if(lookupText.equals("n,"))
			createCandidatesButton("刀力加努女她妙妹始姐婚婦媽忍架皮頗飛");
		else if(lookupText.equals("n."))
			createCandidatesButton("允勇務參又台態柔登發矛矣習翠通鄧預廴厶乄");
		else if(lookupText.equals("n/"))
			createCandidatesButton("了壯孔孤孩孫將巴承收牆狀獎疏眉蛋裝違阝凵卩丩");
		else if(lookupText.equals("nm"))
			createCandidatesButton("刁即司問局展己已建張強改書民選那開間");
		else if(lookupText.equals("nn"))
			createCandidatesButton("乜幾樂約紅組結絕給統經線總變院除際響巜㔾");
*/
