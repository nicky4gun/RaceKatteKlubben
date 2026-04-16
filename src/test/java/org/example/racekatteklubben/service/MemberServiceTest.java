package org.example.racekatteklubben.service;

import org.example.racekatteklubben.models.interfaces.IMemberRepository;
import org.example.racekatteklubben.models.Member;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private IMemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    @Test
    void registerMember_ShouldRegister_ValidMember() {
        String memberName = "John Doe";
        String email = "john@example.com";
        String password = "SecurePass123";
        boolean admin = false;

        when(memberRepository.findMemberByEmail(email)).thenReturn(null);

        memberService.registerMember(memberName, email, password, admin);

        ArgumentCaptor<Member> captor = ArgumentCaptor.forClass(Member.class);
        verify(memberRepository).createMember(captor.capture());
        Member capturedMember = captor.getValue();

        assertEquals(memberName, capturedMember.getMemberName());
        assertEquals(email, capturedMember.getEmail());
        assertTrue(BCrypt.checkpw(password, capturedMember.getPassword()));
        assertFalse(capturedMember.isAdmin());
    }

    @Test
    void registerMember_ShouldNotRegister_EmailExists() {
        String email = "existing@example.com";
        Member existingMember = new Member("Existing", email, "hash", false);
        when(memberRepository.findMemberByEmail(email)).thenReturn(existingMember);

        memberService.registerMember("John", email, "Password123", false);

        verify(memberRepository, never()).createMember(any());
    }

    @Test
    void registerMember_NullName_ShouldReturn_IllegalArgumentException() {
        when(memberRepository.findMemberByEmail(any())).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                memberService.registerMember(null, "test@email.com", "Password123", false)
        );

        assertEquals("Indtast venligst dit navn.", exception.getMessage());
        verify(memberRepository, never()).createMember(any());
    }

    @Test
    void registerMember_ShouldThrowException_PasswordNoLetters() {
        // Arrange
        when(memberRepository.findMemberByEmail("test@example.com")).thenReturn(null);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> memberService.registerMember("John Doe", "test@example.com", "12345678", false)
        );

        assertEquals("Ugyldig adgangskode", exception.getMessage());
        verify(memberRepository, never()).createMember(any());
    }

    @Test
    void loginMember_ShouldLogin_ValidCredentials() {
        String password = "Password123";
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));
        Member member = new Member(1, "John Doe", "john@example.com", hashedPassword, false);

        when(memberRepository.findMemberByEmail("john@example.com")).thenReturn(member);

        Member result = memberService.loginMember("john@example.com", password);

        assertNotNull(result);
        assertEquals(member.getId(), result.getId());
        assertEquals(member.getEmail(), result.getEmail());
        verify(memberRepository).findMemberByEmail("john@example.com");
    }

    @Test
    void loginMember_ShouldReturnNull_EmailNotExists() {
        when(memberRepository.findMemberByEmail("nonexistent@example.com")).thenReturn(null);

        Member result = memberService.loginMember("nonexistent@example.com", "Password123");

        assertNull(result);
        verify(memberRepository).findMemberByEmail("nonexistent@example.com");
    }

    @Test
    void loginMember_ShouldReturnNull_WrongPassword() {
        String hashedPassword = BCrypt.hashpw("CorrectPass123", BCrypt.gensalt(12));
        Member member = new Member(1, "John Doe", "john@example.com", hashedPassword, false);

        when(memberRepository.findMemberByEmail("john@example.com")).thenReturn(member);

        Member result = memberService.loginMember("john@example.com", "WrongPass123");

        assertNull(result);
        verify(memberRepository).findMemberByEmail("john@example.com");
    }

    @Test
    void updateMember_ShouldUpdate_WithoutNewPassword() {
        String originalPassword = "OriginalPass123";
        String hashedOriginalPassword = BCrypt.hashpw(originalPassword, BCrypt.gensalt(12));
        Member existingMember = new Member(1, "John Doe", "john@example.com", hashedOriginalPassword, false);

        when(memberRepository.findMemberById(1)).thenReturn(existingMember);

        memberService.updateMember(1, "Jane Doe", "jane@example.com", null, null);

        ArgumentCaptor<Member> captor = ArgumentCaptor.forClass(Member.class);
        verify(memberRepository).updateMember(captor.capture());
        Member updatedMember = captor.getValue();

        assertEquals("Jane Doe", updatedMember.getMemberName());
        assertEquals("jane@example.com", updatedMember.getEmail());
        assertEquals(hashedOriginalPassword, updatedMember.getPassword());
        verify(memberRepository).findMemberById(1);
    }

    @Test
    void deleteMember_ShouldDeleteMember_Successfully() {
        memberService.removeMember(1);
        verify(memberRepository).removeMember(1);
    }

    @Test
    void validateEmail_ShouldValidate_ValidFormat() {
        when(memberRepository.findMemberByEmail(anyString())).thenReturn(null);

        assertDoesNotThrow(() ->
                memberService.registerMember("John", "user@example.com", "Password123", false)
        );

        assertDoesNotThrow(() ->
                memberService.registerMember("Jane", "user.name@example.co.uk", "Password123", false)
        );

        assertDoesNotThrow(() ->
                memberService.registerMember("Bob", "user+tag@example.com", "Password123", false)
        );
    }

    @Test
    void ValidatePassword_ShouldValidate_MinEightCharacters() {
        when(memberRepository.findMemberByEmail("test@example.com")).thenReturn(null);

        assertDoesNotThrow(() ->
                memberService.registerMember("John", "test@example.com", "Pass1234", false)
        );
    }
}