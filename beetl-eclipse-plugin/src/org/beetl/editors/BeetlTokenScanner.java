package org.beetl.editors;

import java.util.Iterator;

import org.beetl.core.parser.BeetlLexer;
import org.beetl.core.parser.BeetlToken;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.ITokenScanner;
import org.eclipse.jface.text.rules.Token;

public class BeetlTokenScanner implements ITokenScanner {

	BeetlTokenSource source = null;
	Iterator<BeetlToken> it = null;
	BeetlToken current = null;
	String type = null;
	int offset = 0;
	public BeetlTokenScanner(String type){
		this.type = type;
	}
	@Override
	public int getTokenLength() {
		// TODO Auto-generated method stub
		return current.end-current.start;
	}

	@Override
	public int getTokenOffset() {
		// TODO Auto-generated method stub
		return offset+current.start;
	}

	@Override
	public IToken nextToken() {
		if(it.hasNext()){
			current = it.next();
			return getTokenByBeetlToken(current);
		}else{
			current = null;
			return Token.EOF;
		}
	
		//return getTokenByBeetlToken(current);
		
		
	}

	@Override
	public void setRange(IDocument arg0, int arg1, int arg2) {
		String text = arg0.get();
		String content = text.substring(arg1,arg1+arg2);
		offset = arg1;
		source = new BeetlTokenSource();
		source.parse(content);
		it = source.tokens.iterator();
	}
	
	private IToken getTokenByBeetlToken( BeetlToken t){
		if(t.type==BeetlLexer.TEXT_TT){
			return new Token(new TextAttribute(ColorManager.instance().getColor(SyntaxColorConstants.STATIC_TEXT)));
			
		}else if(t.type==BeetlLexer.INTEGER_TT){
			return new Token(new TextAttribute(ColorManager.instance().getColor(SyntaxColorConstants.NUMBER)));
			
		}else if(t.type==BeetlLexer.STRING_TT){
			return new Token(new TextAttribute(ColorManager.instance().getColor(SyntaxColorConstants.STRING)));
			
		}
		else{
			return new Token(new TextAttribute(ColorManager.instance().getColor(SyntaxColorConstants.HOLDER)));
			
		}
		//return new ViewToken(t);
		
	
	}
	


}
