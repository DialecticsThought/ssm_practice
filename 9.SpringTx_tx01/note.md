����ʽ����
    ��ǰͨ�����ӵı������д��ʳ�� �滻Ϊֻ��Ҫ����spring�ĸ����������񷽷����ɣ�
    Spring�Զ������������

aop������ͨ������֪ͨ������������£�
    //��ȡ����
    //���÷��Զ� �ύ
    Ŀ�����ִ��
    //�����ύ
    //�쳣�ع�
    //���չر�
����Ч��
@Transactional
public void checkout(String username,String isbn){
//�����
bookDao.updateStock(isbn);
//��۸�
int price = bookDao.getPrice(isbn);
//�����
bookDao.updateBalance(username,price);
}
����������Ĺ̶�ģʽ ��Ϊһ�ֺ��й�ע�� ����ͨ��aop������ģ�黯 ��������Spring AOPʵ������ʽ����

�������� �������������


���ʽ����
try{
//��ȡ����
//���÷��Զ� �ύ
chain.doFilter()
//�ύ
}catch(Exeception e){
//�ع�
}finally{
//�ر����� �ͷ���Դ
}