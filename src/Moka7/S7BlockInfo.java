/**
 * This class was copied from the MOKA 7 Library, available on SourceForge.
 *
 * Original source: https://sourceforge.net/projects/snap7/files/Moka7/1.0.1/moka7-full-1.0.1.zip/download
 * Accessed on: 2024-05-01
 *
 * The original work is credited to the respective authors of the MOKA 7 Library.
 *
 * This file is included and used under the terms of the GNU General Public License v3.0 (GPL-3.0).
 * For the full license text, see: https://www.gnu.org/licenses/gpl-3.0.en.html
 */

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Moka7;
import java.util.Date;
/**
 *
 * @author Davide
 */
public class S7BlockInfo {

    private final int BufSize = 96;
    // MilliSeconds between 1970/1/1 (Java time base) and 1984/1/1 (Siemens base)
    private final long DeltaMilliSecs = 441763200000L; 
    protected byte[] Buffer = new byte[BufSize];       
        
    protected void Update(byte[] Src, int Pos)
    {
        System.arraycopy(Src, Pos, Buffer, 0, BufSize);
    }   
    public int BlkType()
    {
       return Buffer[2];
    }
    public int BlkNumber()
    {
       return S7.GetWordAt(Buffer, 3);
    }
    public int BlkLang()
    {
       return Buffer[1];
    }
    public int BlkFlags()
    {
       return Buffer[0];
    }
    public int MC7Size()  // The real size in bytes
    {
       return S7.GetWordAt(Buffer, 31);
    }
    public int LoadSize()
    {
       return S7.GetDIntAt(Buffer, 5);
    }
    public int LocalData()
    {
       return S7.GetWordAt(Buffer, 29);
    }
    public int SBBLength()
    {
       return S7.GetWordAt(Buffer, 25);
    }
    public int Checksum()
    {
       return S7.GetWordAt(Buffer, 59);
    }
    public int Version()
    {
       return Buffer[57];
    }
    public Date CodeDate()
    {
        long BlockDate = ((long)S7.GetWordAt(Buffer, 17))*86400000L+DeltaMilliSecs;
        return new Date(BlockDate);
    }
    public Date IntfDate()
    {
        long BlockDate = ((long)S7.GetWordAt(Buffer, 23))*86400000L+DeltaMilliSecs;
        return new Date(BlockDate);
    }
    public String Author()
    {
      return S7.GetStringAt(Buffer,33,8);
    }
    public String Family()
    {
      return S7.GetStringAt(Buffer,41,8);
    }
    public String Header()
    {
      return S7.GetStringAt(Buffer,49,8);
    }
    
}
